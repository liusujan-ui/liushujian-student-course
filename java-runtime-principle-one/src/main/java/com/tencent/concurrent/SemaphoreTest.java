package com.tencent.concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 14:59
 */
public class SemaphoreTest {
//    private static Semaphore sp=new Semaphore(6);
     private static LllSemaphore sp = new LllSemaphore(6);
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sp.acquire();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("获取到了db的连接查询。。。"+Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    System.out.println("执行完了"+Thread.currentThread().getName());
                    sp.release();
                }
            }).start();
        }

    }
}
