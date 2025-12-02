package com.tencent.locktest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description  lock和condition联合起来使用
 * @date 2025-12-01 22:47
 */
public class ConditionTest {

    private static ReentrantLock lock=new ReentrantLock();

    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("当前线程："+Thread.currentThread().getName()+"获得锁");
                    condition.await();  // 把 线程挂起
                    System.out.println("当前线程："+Thread.currentThread().getName()+"开始执行~");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });

        thread.start();
        Thread.sleep(2000L);
        System.out.println("休眠2秒，来控制线程~");

        lock.lock();
        condition.signal();
        lock.unlock();
    }



}
