package com.tencent.principle.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author 观自在
 * @description
 * @date 2025-12-27 22:25
 */
@Slf4j
public class NIOServer1 {
    /*
    已经建立连接的集合
     */
    private static ArrayList<SocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // 创建网络服务端
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设置为非阻塞模式
        serverSocketChannel.socket().bind(new InetSocketAddress(8080));//绑定端口
        log.info("启动成功！！！");
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();// 获取新tcp链接通道
            //tcp请求 读取/响应
            if (socketChannel != null) {
                log.info("收到新链接：{}", socketChannel.getRemoteAddress());
                socketChannel.configureBlocking(false);//默认是阻塞的，一定要设置为非阻塞
                channels.add(socketChannel);
            }else {
                //没有新链接的情况下，就去处理现有链接的数据，处理完的就删除掉
                Iterator<SocketChannel> iterator = channels.iterator();
                while (iterator.hasNext()) {
                    SocketChannel ch = iterator.next();
                    try {
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                        if (ch.read(requestBuffer) == 0) {
                            //等于0，代表这个通道没有数据需要处理，那就待会再处理
                            continue;
                        }
                        while (ch.isOpen()&&ch.read(requestBuffer)==-1) {
                            //长连接情况下，需要手动判断数据有没有读取结束（此处做一个简单的判断：超过0字节就认为请求结束了）
                            if (requestBuffer.position()>0) break;
                        }
                        if(requestBuffer.position()==0) continue;//如果没有数据了，则不进行后面的处理
                        requestBuffer.flip();
                        byte[] content = new byte[requestBuffer.limit()];
                        requestBuffer.get(content);
                        log.info("{}",content);
                        log.info("收到数据，来自：{}",ch.getRemoteAddress());

                        //响应结果200
                        String response="HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "hello world!!!";
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        while (buffer.hasRemaining()) {
                            ch.write(buffer);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        // 用到了非阻塞的API，在设计上，和BIO可以有很大的不同，继续改进
    }
}
