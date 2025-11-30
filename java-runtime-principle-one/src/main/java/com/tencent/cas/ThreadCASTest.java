package com.tencent.cas;


import com.tencent.util.Counter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 13:19
 */
public class ThreadCASTest {
    public static void main(String[] args) throws Exception{
        test3();
    }

    public static void test3() throws InterruptedException {
        final Counter counter=new Counter();
        ReentrantLock lock = new ReentrantLock();

        for(int i=0;i<6;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    for (int j = 0; j < 10000; j++) {
                            counter.add();
                        }
                        System.out.println("done...");
                    lock.unlock();
                }
            }).start();
        }

        Thread.sleep(1000L);
        System.out.println(counter.getNum());
    }

    public static void test2() throws InterruptedException {
        final Counter counter=new Counter();

        for(int i=0;i<6;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (Counter.class) {
                        for (int j = 0; j < 10000; j++) {
                            counter.add();
                        }
                        System.out.println("done...");
                    }
                }
            }).start();
        }

        Thread.sleep(1000L);
        System.out.println(counter.getNum());
    }


    public static void test1() throws InterruptedException {
        final Counter counter=new Counter();

        for(int i=0;i<6;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        counter.add();
                    }
                    System.out.println("done...");
                }
            }).start();

        }

        Thread.sleep(1000L);
        System.out.println(counter.getNum());
    }



}
