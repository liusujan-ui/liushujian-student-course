package com.tencent.principle.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author 观自在
 * @description
 * @date 2025-12-27 22:32
 */

@Slf4j
public class NIOClient {

    public static void main(String[] args) throws Exception {
        // 创建网络客户端
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设置为非阻塞模式
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));
        while (!socketChannel.finishConnect()) {
            //没连接上，则一直等待
            Thread.yield();
        }
        Scanner scanner = new Scanner(System.in);
        log.info("请输入：");
        // 发送内容
        String msg=scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        //读取响应
        log.info("收到服务端响应：");
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen()&&socketChannel.read(requestBuffer)!=-1) {
            //长连接情况下，需要手动判断数据有没有读取结束（此处做一个简单的判断：超过0字节就认为请求结束了）
            if (requestBuffer.position()>0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        log.info("{}",new String(content));
        scanner.close();
        socketChannel.close();
    }
}
