package com.tencent.CPUFasterTest;

import sun.misc.Contended;

/**
 * @author 观自在
 * @description 伪共享 -- CPU缓存
 * @date 2025-12-10 08:21
 */
public class FalseSharingTest {
    static class Entry{

        @Contended
        public volatile long value;

        //加上下面那一行，和不加的差距也是很明显，这是为什么呢？--- 缓存行填充，也可以使用contended注解，java8出来的
//        public volatile long temp1,temp2,temp3,temp4,temp5,temp6,temp7;
    }

    public static Entry[] array = new Entry[2];

    public static void main(String[] args) throws InterruptedException {
        array[0] = new Entry();
        array[1] = new Entry();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000000L; i++) {
                array[0].value = i;
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000000L; i++) {
                array[1].value = i;
            }
        });

        long begin = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        long end = System.currentTimeMillis();
        System.out.println(end - begin);


    }
}
