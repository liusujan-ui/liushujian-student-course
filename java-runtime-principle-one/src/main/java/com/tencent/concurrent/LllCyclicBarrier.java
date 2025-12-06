package com.tencent.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 15:43
 */
public class LllCyclicBarrier {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  private int count = 0; //批次

  private final int parties; // 多少线程准备就绪

    private Object generation=new Object();

    public LllCyclicBarrier(int parties) {
        this.parties = parties;
    }

    public void await()  {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            final Object g=generation;

            int index = ++count;
            if (index == parties) {
                nextGeneration();
                return;
            }

            while (true){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (g!=generation) {
                    return;
                }
            }
        }finally {
            lock.unlock();
        }

    }

    public void nextGeneration() {
        condition.signalAll();
        count = 0;
        generation = new Object();
    }
}
