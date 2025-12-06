package com.tencent.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 15:23
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(6); //
        LllCountDownLatch latch = new LllCountDownLatch(6);
        //还有一种方式，将一个活分为多段，每个线程去干一段
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                latch.countDown(); //一直到计数器，减一到0为止
                try {
                    latch.await(); // 阻塞
                    System.out.println("线程："+Thread.currentThread().getName()+"执行完成");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        System.out.println("开始干活。。。。");




        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println("开始准备....");
                latch.countDown();
            }).start();
            Thread.sleep(1000);
        }

        latch.await(); // 说明countDownLatch这个计数器，实际上是【所有线程可见】的计数器

        System.out.println("开始干活。。。。");
    }
}
