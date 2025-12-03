package com.tencent.locktest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 观自在
 * @description 手写读写锁
 * @date 2025-12-03 23:12
 */
public class ReadWriteLockTest {

    volatile AtomicInteger readCount = new AtomicInteger(0);
    AtomicInteger writeCount = new AtomicInteger(0);

    //独占锁 拥有者
    AtomicReference<Thread> owner = new AtomicReference<>();

    //等待队列
    public volatile LinkedBlockingQueue<WaitNode> waiters=new LinkedBlockingQueue();
    class WaitNode{
        int type=0; //0 为想获取独占锁的线程 ， 1为想获取共享锁的线程
        Thread thread=null;
        int arg=0;
        public WaitNode(Thread thread,int type,int arg){
            this.thread=thread;
            this.type=type;
            this.arg=arg;
        }

    }

// 获取独占锁
    public void lock(){
        int arg=1;
        //尝试获取独占锁，若成功，退出方法， 若失败
        if (!tryLock(arg)){
            //标记独占锁
            WaitNode waitNode=new WaitNode(Thread.currentThread(),0,arg);
            waiters.add(waitNode);

            //循环尝试拿锁
            for (;;){
                //若队列头部是当前线程
                WaitNode head=waiters.peek();
                if (head!=null && head.thread==Thread.currentThread()){
                    if(!tryLock(arg)){  //再次尝试获取独占锁
                        LockSupport.park();// 若失败挂起线程
                    }else { //若成功获取
                        waiters.poll();  //当前线程从队列头部移除
                        return; // 并退出方法
                    }
                }else { //若不是队列头部元素
                    LockSupport.park(); //将当前线程挂起
                }
            }
        }
    }

    //释放独占锁
    public boolean unlock(){
        int arg=1;
        //尝试释放独占锁，若失败返回true，若失败。。。
        if(tryUnlock(arg)){
            WaitNode next=waiters.peek();
            if (next!=null ){
                Thread th=next.thread;
                LockSupport.unpark(th);
            }
            return true;
        }
        return false;
    }

    // 尝试获取独占锁
    public boolean tryLock(int acquires){
        //如果read count!=0 返回false
        if (readCount.get() != 0) {
            return false;
        }
        int wct=writeCount.get(); //拿到独占锁，当前状态

        if (wct == 0) {
            if (writeCount.compareAndSet(wct, wct + acquires)) {
                owner.set(Thread.currentThread());
                return true;
            }
        }else if (owner.get() == Thread.currentThread()) {
            writeCount.set(wct + acquires); //修改count值
            return true;
        }
        return false;
    }

    //尝试释放锁
    public boolean tryUnlock(int releases){
        //若当前线程没有持有独占锁
        if (owner.get() != Thread.currentThread()) {
            throw new IllegalMonitorStateException();
        }

        int wc=writeCount.get();
        int next=wc-releases;
        writeCount.set(next);

        if (next==0) {
            owner.compareAndSet(Thread.currentThread(), null);
            return true;
        }else{
            return false;
        }
    }

    //获取共享锁
    public void lockShared(){
        int arg=1;
        if (tryLockShared(arg) < 0) {
            WaitNode node=new WaitNode(Thread.currentThread(),1,arg);
            waiters.add(node);

            for (;;){
                WaitNode head=waiters.peek();
                if (head!=null && head.thread==Thread.currentThread()){
                    if (tryLockShared(arg)>=0) {
                        waiters.poll();

                        WaitNode next=waiters.peek();
                        if (next != null && next.type == 1) {
                            LockSupport.unpark(next.thread);
                        }
                        return;
                    }else {
                        LockSupport.park();
                    }
                }else {
                    LockSupport.park();
                }
            }
        }
    }

    //解锁共享锁
    public boolean unLockShared(){
        int arg=1;
        if (tryUnlockShared(arg)) {
            WaitNode next=waiters.peek();
            if (next != null) {
                LockSupport.unpark(next.thread);
            }
            return true;
        }
        return false;
    }

    public int tryLockShared(int acquires){
        for (;;){
            if(writeCount.get()!=0&&owner.get()!=Thread.currentThread())
                return -1;

            int rct=readCount.get();
            if (readCount.compareAndSet(rct,rct+acquires)) {
                return 1;
            }

        }
    }


    //尝试解锁共享锁
    public boolean tryUnlockShared(int releases){
        for (;;){
            int rc=readCount.get();
            int next=rc-releases;
            if (readCount.compareAndSet(rc,next)) {
                return next==0;
            }
        }
    }
}
