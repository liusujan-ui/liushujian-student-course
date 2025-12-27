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

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8080);
        OutputStream out = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        log.info("请输入：");
        String msg = scanner.nextLine();
        out.write(msg.getBytes(charset));
        scanner.close();
        socket.close();
    }
}
