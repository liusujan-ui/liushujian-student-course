package com.tencent.cas;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 14:28
 */
public class LongAddr {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        LongAccumulator accumulator = new LongAccumulator(
                (x, y) -> {
                    System.out.println("x:" + x + ",y:" + y);
                    return x + y;
                }, 0L);
        for(int i=0;i<5;i++) {
            accumulator.accumulate(1); // 在上面规则的结果会作为参数第二次进入
        }
        System.out.println(accumulator.get());

    }

    public static void test() throws InterruptedException {
        LongAdder addr= new LongAdder();
        for(int i=0;i<5;i++) {
            new Thread(()-> {
                long startTime=System.currentTimeMillis();
                while (System.currentTimeMillis()-startTime<2000) {
                    addr.increment();
                }
            }).start();

        }


        Thread.sleep(3000);
        System.out.println(addr.sum());
    }

}
