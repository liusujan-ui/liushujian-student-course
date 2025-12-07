package com.tencent.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author 观自在
 * @description
 * @date 2025-12-07 15:24
 */
public class FileTest {

    private static String readFileContent(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        return contentBuilder.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        String path="src/main/java/com/tencent/files/test.txt";

        String s = readFileContent(path);
        System.out.println(s);
    }
}
