package com.tencent.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 14:05
 */
public class CounterAtomic {

    AtomicInteger i=new AtomicInteger(0);


    public static void main(String[] args) throws Exception{
        test();
    }
    public void inc() {i.incrementAndGet();}


    public static void test() throws InterruptedException {
        final CounterAtomic counter=new CounterAtomic();

        for(int i=0;i<5;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        counter.inc();
                    }
                    System.out.println("done...");
                }
            }).start();
        }
        Thread.sleep(1000L);
        System.out.println(counter.i);
    }
}
