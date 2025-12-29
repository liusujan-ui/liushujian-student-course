package com.tencent.principle.bioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 观自在
 * @description
 * @date 2025-12-27 22:25
 */
@Slf4j
public class BIOServer {
    /**
     * 程序的入口方法，启动一个TCP服务器。
     * 该服务器在 8080 端口监听客户端连接，并为每个连接读取并打印接收到的数据。
     * 这是一个简单的单线程服务器，一次只能处理一个客户端请求。
     *
     * @param args 命令行参数（本例中未使用）
     * @throws Exception 如果在服务器套接字操作或I/O操作中发生任何错误
     */
    public static void main(String[] args) throws Exception {
        // 创建一个服务器套接字，并绑定到本地的 8080 端口，开始监听客户端的连接请求
        ServerSocket serverSocket = new ServerSocket(8080);
        log.info("服务器启动成功");

        // 循环等待客户端连接，直到服务器套接字被关闭
        while (!serverSocket.isClosed()) {
            // 接受（阻塞）客户端的连接请求，返回一个与该客户端通信的Socket对象
            // 如果没有客户端连接，程序将在此处暂停等待
            Socket request = serverSocket.accept(); // 阻塞，就是一个线程只能处理一个请求
            log.info("收到新链接：{}", request.toString());

            try {
                // 接收数据、打印
                // 从客户端Socket中获取输入流，用于读取客户端发送的数据
                InputStream inputStream = request.getInputStream(); // net+io
                // 将字节输入流包装为字符输入流，并使用缓冲区提高读取效率
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                String msg;
                // 循环逐行读取客户端发送的数据
                while ((msg = reader.readLine()) != null) {
                    // 如果读到空行，则认为HTTP请求头结束或消息结束，跳出循环
                    if (msg.length() == 0) {
                        break;
                    }
                    // 打印从客户端接收到的每一行数据
                    log.info(msg);
                }
                log.info("收到数据，来自 {}", request.toString());
            } catch (Exception e) {
                // 捕获并打印在处理单个客户端连接时可能发生的任何异常
                e.printStackTrace();
            } finally {
                // 在 finally 块中确保关闭与当前客户端的连接，释放资源
                request.close();
            }
        }
        // 如果循环退出（例如serverSocket被关闭），则关闭服务器套接字
        serverSocket.close();
    }

}
