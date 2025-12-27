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
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        log.info("服务器启动成功");
        while (!serverSocket.isClosed()) {
            Socket request = serverSocket.accept(); // 阻塞
            log.info("收到新链接：{}",request.toString());
            threadPool.execute(() -> {
                try {
                    //接收数据、打印
                    InputStream inputStream = request.getInputStream(); // net+io
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg;
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) {
                            break;
                        }
                        log.info(msg);
                    }
                    log.info("收到数据，来自 {}",request.toString());
                    // 相应结果200
                    OutputStream outputStream = request.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    outputStream.write("hello world!!!".getBytes());
                    outputStream.flush();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        request.close();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        serverSocket.close();
    }
}
