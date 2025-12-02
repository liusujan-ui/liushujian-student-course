package com.tencent.locktest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-12-01 22:39
 */
public class LockTest {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();

        Thread th=new Thread(()->{
            boolean rs=false;
            try {
//                rs=lock.tryLock(); //浅尝辄止
//                lock.lockInterruptibly();
                lock.lock();// 不死不休
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("是否获取到锁："+rs);
        });
        th.start();

        Thread.sleep(2000L);
        th.interrupt(); //中断线程运行
        System.out.println("th线程也中断了");

        Thread.sleep(5000L);
        lock.unlock();
    }
}
