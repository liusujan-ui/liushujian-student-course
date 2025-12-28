package com.tencent.principle.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 观自在
 * @description
 * @date 2025-12-28 12:10
 */
@Slf4j
public class NIOServer2 {
    public static void main(String[] args) throws IOException {
        //1.创建网络服务端ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);// 设置为非阻塞模式

        //2.构建一个selector选择器，并且将channel注册上去
        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, serverSocketChannel);//将serverSocketChannel注册到selector
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);//对serverSocketChannel上面的accept事件感兴趣（serverSocketChannel只能支持accept事件）

        //3.绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        log.info("启动成功！！！");

        while (true) {
            //不再轮询通道，改用下面轮询事件的方式，select方法有阻塞效果，知道有事件通知才会有返回
            selector.select();
            //获取事件
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历查询结果e
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                //关注read和accept两个事件
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.attachment();
                    //将拿到的客户端连接通道，注册到selector上面
                    SocketChannel clientSocketChannel = server.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector, SelectionKey.OP_READ, clientSocketChannel);
                    log.info("收到新链接：{}",clientSocketChannel.getRemoteAddress());
                }

                if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.attachment();
                    try {
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                        while (socketChannel.isOpen()&&socketChannel.read(requestBuffer)!=-1) {
                            //长连接情况下，需要手动判断数据有没有读取结束（此处做一个简单的判断，超过0字节就认为请求结束了）
                            if(requestBuffer.position()>0) break;
                        }
                        if(requestBuffer.position()==0) continue;//如果没有数据了，则不进行后面的处理
                        requestBuffer.flip();
                        byte[] content = new byte[requestBuffer.limit()];
                        requestBuffer.get(content);
                        log.info("{}",content);
                        log.info("收到数据，来自：{}",socketChannel.getRemoteAddress());

                        //响应结果200
                        String response="HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "hello world!!!";
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        while (buffer.hasRemaining()) {
                            socketChannel.write(buffer);
                        }
                    }catch (Exception e){
                        key.cancel();
                    }
                }
            }
            selector.selectNow();
        }
        //问题：此处一个selector监听所有事件，一个线程处理所有请求事件，会成为瓶颈！要有多线程的运用
    }
}
