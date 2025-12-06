package com.tencent.futureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 18:42
 */
public class LllFutureTask<T> implements Runnable{

    private Callable<T> callable;

    T result;

    volatile String state="NEW";

    LinkedBlockingQueue<Thread> waiters=new LinkedBlockingQueue<>();

    public LllFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    public T get()  { // 阻塞，等待run方法执行完毕
        if("END".equals(state)){
            return result;
        }
        while(!"END".equals(state)){  //开始执行阻塞
            // 准备一个容器，通过这个容器来存放线程
            waiters.offer(Thread.currentThread());
            LockSupport.park();
        }
        return result;
    }

    @Override
    public void run() {
        try {
            result=callable.call(); // 一定要保证call方法确认有值了，才能执行get方法
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            state="END";
        }
        Thread waiter=waiters.poll();
        while (waiter!=null){
            LockSupport.unpark(waiter);
            waiter=waiters.poll();
        }
    }

}
