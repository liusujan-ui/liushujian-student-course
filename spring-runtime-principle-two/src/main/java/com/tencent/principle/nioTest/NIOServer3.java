package com.tencent.principle.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 观自在
 * @description NIO selector 多路复用reactor线程模型
 * @date 2025-12-28 12:32
 */
@Slf4j
public class NIOServer3 {
    //处理业务操作的线程
    private static ExecutorService workPool = Executors.newCachedThreadPool();

    //封装了selector.select()等事件轮询的代码
    abstract class ReactorThread extends Thread {
        Selector selector;
        LinkedBlockingQueue<Runnable> taskQueue=new LinkedBlockingQueue<>();

        //selector监听事件后，调用这个方法
        public abstract void handler(SelectableChannel channel) throws Exception;

        private ReactorThread() throws IOException {
            selector = Selector.open();
        }

        volatile boolean running = true;

        @Override
        public void run() {
            //轮询selector事件
            while (running) {
                try {
                    //执行队列中的任务
                    Runnable task;
                    while ((task = taskQueue.poll()) != null) {
                        task.run();
                    }
                    selector.select(1000);

                    //获取查询结果
                    Set<SelectionKey> selected = selector.selectedKeys();
                    //遍历查询结果
                    Iterator<SelectionKey> iterator = selected.iterator();
                    while (iterator.hasNext()) {
                        //被封装的查询结果
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        int readyOps = key.readyOps();
                        //关注read和accept两个事件
                        if ((readyOps&(SelectionKey.OP_READ|SelectionKey.OP_ACCEPT))!=0||readyOps==0){
                            try {
                                SelectableChannel channel = (SelectableChannel) key.attachment();
                                channel.configureBlocking(false);
                                handler(channel);
                                if (!channel.isOpen()) {
                                    key.cancel(); //如果关闭了，取消这个订阅
                                }
                            }catch (Exception e){
                                key.cancel();//如果有异常，就取消这个key的订阅
                            }
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // ... existing code in ReactorThread abstract class ...

        /**
         * 将通道注册到当前Reactor线程的Selector上。
         * 该方法是线程安全的，它会将注册操作封装成任务提交给Reactor线程自身执行，
         * 以避免与Selector.select()操作产生锁竞争。
         * 调用此方法的线程会阻塞，直到注册完成并返回SelectionKey。
         *
         * @param channel 需要注册的通道
         * @return 注册成功后返回的SelectionKey
         * @throws Exception 如果注册过程中发生错误
         */
        public SelectionKey register(SelectableChannel channel) throws Exception {
            // 使用一个单元素数组来在Lambda表达式内外共享结果
            final SelectionKey[] keyRef = new SelectionKey[1];
            final Exception[] exceptionRef = new Exception[1];

            // 将注册操作封装成任务，提交给Reactor线程的任务队列
            Runnable task = () -> {
                try {
                    // 在Reactor线程中执行实际的注册操作
                    // 将channel自身作为attachment，方便在handler中获取
                    keyRef[0] = channel.register(selector, 0, channel);
                } catch (Exception e) {
                    exceptionRef[0] = e;
                }
            };
            taskQueue.add(task);

            // 唤醒可能在selector.select()上阻塞的Reactor线程，使其立即处理任务
            selector.wakeup();

            // 等待注册任务完成
            // 由于任务是由Reactor线程异步执行的，这里需要轮询等待结果
            while (keyRef[0] == null && exceptionRef[0] == null) {
                // 让出CPU，避免忙等待
                Thread.yield();
            }

            // 检查在注册过程中是否发生了异常
            if (exceptionRef[0] != null) {
                throw exceptionRef[0];
            }

            return keyRef[0];
        }

        /**
         * 启动Reactor线程。
         * 如果线程还未启动，则调用start()方法启动它。
         * 这个方法应该是幂等的，多次调用不会产生副作用。
         */
        public void doStart() {
            // isAlive()方法用于判断线程是否已经启动（即是否处于RUNNABLE状态）
            if (!this.isAlive()) {
                this.start();
            }
        }



    }

    private ServerSocketChannel serverSocketChannel;
    //1.创建多个线程 - accept处理reactor线程（accept线程）
    private ReactorThread[] mainReactorThreads = new ReactorThread[1];
    //2.创建多个线程 - io处理reactor线程（I/O线程）
    private ReactorThread[] subReactorThreads = new ReactorThread[8];

    //初始化线程组
    private void newGroup() throws IOException {
        //创建IO线程，负责处理客户端连接以后socketChannel的IO读写
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i]=new ReactorThread() {
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //work线程只负责处理IO处理，不处理accept事件
                    SocketChannel ch = (SocketChannel) channel;
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    while (ch.isOpen()&&ch.read(requestBuffer)!=-1) {
                        if (requestBuffer.position()>0) break;
                    }
                    if (requestBuffer.position()==0) return;//如果没数据，则不进行后面的处理
                    requestBuffer.flip();
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    log.info("{}",new String(content));
                    log.info("{} 收到数据，来自：{}",Thread.currentThread().getName(),ch.getRemoteAddress());

                    //TODO 业务操作、数据库、接口...
                    workPool.submit(()->{});

                    //响应结果200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "hello world!!!";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()) {
                        ch.write(buffer);
                    }
                }
            };
        }

        //创建mainReactor线程，只负责处理ServerSocketChannel
        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i]=new ReactorThread() {
                AtomicInteger incr=new AtomicInteger(0);
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //只做请求分发，不做具体的数据读取
                    ServerSocketChannel ch = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = ch.accept();
                    socketChannel.configureBlocking(false);
                    //收到连接通知后，分发给IO线程继续去读取数据
                    int index = incr.getAndIncrement() % subReactorThreads.length;
                    ReactorThread workEventLoop = subReactorThreads[index];
                    workEventLoop.doStart();
                    SelectionKey selectionKey = workEventLoop.register(socketChannel);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    log.info("{} 收到新链接：{}",Thread.currentThread().getName(),socketChannel.getRemoteAddress());
                }
            };
        }
    }

    //初始化channel，并且绑定一个eventLoop线程
    private void initAndRegister() throws Exception {
        //1.创建ServerSocketChannel
        serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        //2.将serverSocketChannel注册到selector上
        int index = new Random().nextInt(mainReactorThreads.length);
        mainReactorThreads[index].doStart();
        SelectionKey selectionKey = mainReactorThreads[index].register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
    }

    //绑定端口
    private void bind() throws IOException {
        // 1.正式绑定端口，对外服务
        serverSocketChannel.bind(new InetSocketAddress(8080));
        log.info("启动完成，端口8080");
    }

    public static void main(String[] args) throws Exception {
        NIOServer3 nioServer3 = new NIOServer3();
        nioServer3.newGroup();//1.创建main和sub两个线程组
        nioServer3.initAndRegister();//2.创建serverSocketChannel，注册到mainReactor线程上的selector上
        nioServer3.bind();//3.为serverSocketChannel绑定端口
    }
}
