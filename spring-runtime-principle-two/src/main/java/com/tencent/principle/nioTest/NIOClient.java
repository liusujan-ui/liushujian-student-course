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

    /**
     * 使用 Java NIO 的非阻塞 SocketChannel 创建一个简单的客户端。
     * 该客户端连接到本地服务器，发送用户输入的消息，并打印服务器的响应。
     *
     * @param Exception 可能会因网络连接或I/O操作抛出异常
     */
    public static void main(String[] args) throws Exception {
        // 创建一个网络客户端 SocketChannel。
        // SocketChannel 是 Java NIO 中用于 TCP 网络连接的通道。
        SocketChannel socketChannel = SocketChannel.open();

        // 将 SocketChannel 设置为非阻塞模式。
        // 在非阻塞模式下，所有连接、读写操作都会立即返回，而不会等待操作完成。
        // 这使得单个线程可以管理多个 Channel 连接。
        socketChannel.configureBlocking(false);

        // 尝试连接到指定的服务器地址和端口。
        // 由于是非阻塞模式，此调用会立即返回，连接操作在后台异步进行。
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        // 循环检查连接是否建立完成。
        // finishConnect() 方法用于完成连接过程。在非阻塞模式下，需要反复调用它直到返回 true。
        while (!socketChannel.finishConnect()) {
            // 如果连接尚未建立，则让出当前 CPU 时间片，避免空转消耗过多 CPU 资源。
            Thread.yield();
        }

        // 创建一个 Scanner 对象，用于从控制台读取用户输入。
        Scanner scanner = new Scanner(System.in);
        log.info("请输入：");

        // 发送内容
        String msg = scanner.nextLine();
        // 使用 ByteBuffer.wrap() 方法将字符串字节数组包装成一个 ByteBuffer。
        // 这是一种高效的方式，避免了额外的内存拷贝。
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        // 循环将缓冲区中的数据写入 SocketChannel。
        // write() 方法不保证能一次性写入所有数据，可能只写入部分字节。
        // 因此，需要通过 buffer.hasRemaining() 检查是否还有未写入的数据，并循环写入直到完成。
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        // 读取服务器的响应
        log.info("收到服务端响应：");
        // 分配一个 1024 字节的 ByteBuffer 用于接收服务器响应数据。
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

        // 循环从 SocketChannel 读取数据到 requestBuffer 中。
        // socketChannel.read() 返回读取到的字节数。如果返回 -1，表示对端已关闭连接。
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 在长连接或需要处理分帧协议的场景下，需要手动判断一次完整的响应是否已经读取完毕。
            // 此处为了演示，做了一个简单的判断：只要读取到了任何数据（position > 0），就认为本次响应结束并跳出循环。
            // 在实际应用中，这个逻辑会复杂得多，例如根据协议头中的长度字段来判断。
            if (requestBuffer.position() > 0) break;
        }

        // 切换 requestBuffer 到读取模式，准备从中读取数据。
        requestBuffer.flip();

        // 创建一个字节数组，其大小等于缓冲区中有效数据的长度（limit）。
        byte[] content = new byte[requestBuffer.limit()];
        // 将缓冲区中的数据读取到字节数组中。
        requestBuffer.get(content);
        // 将字节数组转换为字符串并打印出来。
        log.info("{}", new String(content));

        // 关闭资源。
        scanner.close();
        socketChannel.close();
    }

}
