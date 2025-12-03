package com.tencent.locktest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 观自在
 * @description 实现ReenTrantlock
 * @date 2025-12-02 22:45
 */
public class ReenTrantLockTest implements Lock {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    AtomicReference<Thread> owner=new AtomicReference<>();

    AtomicInteger count=new AtomicInteger();

    private LinkedBlockingQueue<Thread> waiters=new LinkedBlockingQueue<>();

    public void tryUnlock(){
        lock.writeLock().lock();
        lock.writeLock().unlock();
        lock.readLock().lock();
        lock.readLock().unlock();
    }
    @Override
    public void lock() {
        if(!tryLock()){
            //加入等待队列
            waiters.offer(Thread.currentThread());

            while (true){
                Thread head = waiters.peek();
                if (head == Thread.currentThread()){
                    if (!tryLock()){
                        //阻塞
                        LockSupport.park();
                    }else {
                        waiters.poll();
                        return;
                    }
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        int ct=count.get();
        if(ct!=0){  //被占用
            //是不是自己占用
            if(Thread.currentThread()==owner.get()){
                count.set(ct+1);
                return true;
            }else {
                return false;
            }
        }else {
            if(count.compareAndSet(ct,ct+1)){
                owner.set(Thread.currentThread());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (owner.get() == Thread.currentThread()){
            throw new IllegalMonitorStateException();
        }else {
            int ct=count.get();
            int nextc=ct-1;
            count.set(nextc);

            if (nextc==0){
                owner.compareAndSet(Thread.currentThread(),null);
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public void unlock() {
        if (tryLock()){
            Thread head=waiters.peek();
            if (head != null) {
                LockSupport.unpark(head);
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
