package com.tencent.threadTest;

import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description JMM可见性问题
 * volatile:可见性问题：让一个线程对共享变量的修改，能够及时的被其他线程看到。
 * 1.禁止缓存
 * 2.对volatile变量相关的指令不做重排序
 *
 * @date 2025-11-29 19:17
 */
public class VolatileTest {

    public volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        VolatileTest demo = new VolatileTest();
        System.out.println("代码开始了+++++");

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (demo.flag) {
                    i++;
                }
                System.out.println(i);
            }
        }).start();

        TimeUnit.SECONDS.sleep(2);

        demo.flag = false;

        System.out.println("被置为false了+++++++");
    }
}
