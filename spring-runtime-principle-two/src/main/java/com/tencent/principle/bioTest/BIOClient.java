package com.tencent.principle.bioTest;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author 观自在
 * @description
 * @date 2025-12-27 22:32
 */

@Slf4j
public class BIOClient {
    private static Charset charset = Charset.forName("UTF-8");

        /**
     * 程序的入口方法。
     * 该方法连接到本地服务器的 8080 端口，然后从控制台读取用户输入，
     * 并将输入的内容发送到服务器。
     *
     * @param args 命令行参数（本例中未使用）
     * @throws Exception 如果在套接字通信或I/O操作中发生任何错误
     */
    public static void main(String[] args) throws Exception {
        // 创建一个套接字，连接到本地主机（localhost）的 8080 端口
        Socket socket = new Socket("localhost", 8080);
        // 获取该套接字的输出流，用于向服务器发送数据
        OutputStream out = socket.getOutputStream();

        // 创建一个扫描器，用于从标准输入（控制台）读取用户输入
        Scanner scanner = new Scanner(System.in);
        log.info("请输入：");
        // 读取用户在控制台输入的一行文本
        String msg = scanner.nextLine();
        // 将读取到的字符串消息使用指定的字符集转换为字节数组，并通过输出流发送出去
        out.write(msg.getBytes(charset));

        // 关闭扫描器，释放与其相关的资源
        scanner.close();
        // 关闭套接字连接，释放与其相关的资源
        socket.close();
    }

}
