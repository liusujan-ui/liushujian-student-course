package com.tencent.principle.bioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 观自在
 * @description
 * @date 2025-12-27 22:25
 */
@Slf4j
public class BIOServer2 {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    /**
     * 程序的入口方法，启动一个基于线程池的简单HTTP服务器。
     * 该服务器在 8080 端口监听客户端连接，接收HTTP请求，并回复一个固定的 "200 OK" 响应。
     * 每个客户端连接都由线程池中的一个独立线程处理，以实现并发。
     *
     * @param args 命令行参数（本例中未使用）
     * @throws Exception 如果在服务器套接字操作中发生任何错误
     */
    public static void main(String[] args) throws Exception {
        // 创建一个服务器套接字，并绑定到本地的 8080 端口，开始监听客户端的连接请求
        ServerSocket serverSocket = new ServerSocket(8080);
        log.info("服务器启动成功");

        // 循环等待客户端连接，直到服务器套接字被关闭
        while (!serverSocket.isClosed()) {
            // 接受（阻塞）客户端的连接请求，返回一个与该客户端通信的Socket对象
            Socket request = serverSocket.accept(); // 阻塞
            log.info("收到新链接：{}", request.toString());

            // 将处理客户端连接的任务提交给线程池执行，以实现并发处理
            threadPool.execute(() -> {
                try {
                    // 接收数据、打印
                    // 从客户端Socket中获取输入流，用于读取客户端发送的HTTP请求数据
                    InputStream inputStream = request.getInputStream(); // net+io
                    // 将字节输入流包装为字符输入流，并使用缓冲区提高读取效率
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg;
                    // 循环逐行读取HTTP请求头，直到遇到空行（表示请求头结束）
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) {
                            break;
                        }
                        // 打印从客户端接收到的每一行请求头信息
                        log.info(msg);
                    }
                    log.info("收到数据，来自 {}", request.toString());

                    // 相应结果200
                    // 从客户端Socket中获取输出流，用于向客户端发送HTTP响应
                    OutputStream outputStream = request.getOutputStream();
                    // 写入HTTP响应状态行
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    // 写入HTTP响应头，这里声明了响应体的长度为11个字节
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    // 写入响应体内容
                    outputStream.write("hello world!!!".getBytes());
                    // 刷新输出流，确保所有数据都立即发送给客户端
                    outputStream.flush();
                } catch (Exception e) {
                    // 捕获并打印在处理单个客户端连接时可能发生的任何异常
                    e.printStackTrace();
                } finally {
                    // 在 finally 块中确保关闭与当前客户端的连接，释放资源
                    try {
                        request.close();
                    } catch (Exception e) {
                        // 捕获并打印关闭连接时可能发生的异常
                        e.printStackTrace();
                    }
                }
            });
        }
        // 如果循环退出（例如serverSocket被关闭），则关闭服务器套接字
        serverSocket.close();
    }

}
