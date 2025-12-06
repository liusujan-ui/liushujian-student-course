package com.tencent.concurrent;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 15:53
 */
public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        LllCyclicBarrier barrier = new LllCyclicBarrier(4);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                barrier.await();
                System.out.println("任务开始执行..."+Thread.currentThread().getName());
            }).start();
            Thread.sleep(500L);
        }
    }

}
