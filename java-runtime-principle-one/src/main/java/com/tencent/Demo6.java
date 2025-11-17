package com.tencent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author 观自在
 * @date 2025-11-17 22:05
 *
 * 线程的通信方式：
 * 1.文件共享
 * 2.网络共享
 * 3.共享变量
 * 4.jdk提供的线程协调API
 */
public class Demo6 {
    public static void main(String[] args) {
        fun1();
    }

/*
   1 .文件共享
 */

    public static void fun1(){
//        线程1-写入数据
        new Thread(()->{
            try {
                while (true){
                    Files.write(Paths.get("Demo7.log"),
                            ("当前时间"+String.valueOf(System.currentTimeMillis())).getBytes());
                    Thread.sleep(1000L);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        线程2-读取数据
        new Thread(()->{
            try {
                while (true){
                    Thread.sleep(1000L);
                    byte[] bytes = Files.readAllBytes(Paths.get("Demo7.log"));
                    System.out.println(new String(bytes));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


/*
   2 .变量共享
 */
}
