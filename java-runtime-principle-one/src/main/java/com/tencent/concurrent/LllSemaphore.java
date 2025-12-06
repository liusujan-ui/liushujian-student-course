package com.tencent.concurrent;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 15:07
 */
public class LllSemaphore {

    private Sync sync;

    public LllSemaphore(int permits) {
        sync= new Sync(permits);
    }

    public void acquire() {
        sync.acquireShared(1);
    }

    public void release() {
        sync.releaseShared(1);
    }


    class Sync extends AbstractQueuedSynchronizer{
        private int permits;

        public Sync(int permits) {
            this.permits = permits;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            //定义自己的方法
            int state = getState();
            int nextState=state+arg;

            if (nextState<=permits) {
                if(compareAndSetState(state,nextState)) {
                return 1;
                }
            }
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int state = getState();
            if (compareAndSetState(state,state-arg)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
