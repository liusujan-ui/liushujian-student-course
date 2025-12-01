package com.tencent.cas.lockdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-12-01 11:07
 */
public class ReentrantLockDemo {



     private static int i=0;
     private final static Lock lock = new ReentrantLock();


    public static void main(String[] args) throws InterruptedException {
        recursion();
    }
     public static void recursion() throws InterruptedException {
         lock.lock();
         i++;
         System.out.println(i);
         TimeUnit.MICROSECONDS.sleep(1000L);
         recursion();
         lock.unlock();
     }
}
