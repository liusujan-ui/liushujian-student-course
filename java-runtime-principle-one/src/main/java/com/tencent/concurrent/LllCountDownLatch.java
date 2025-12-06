package com.tencent.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 15:34
 */
public class LllCountDownLatch {
    private Sync sync;

    public LllCountDownLatch(int count) {
        sync = new Sync(count);
    }

    public void countDown() {
        sync.releaseShared(1);
    }

    public void await(){
        sync.acquireShared(1);
    }


    class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            setState(count);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                int state = getState();
                if (state == 0) {
                    return false;
                }
                int nextState = state - 1;
                if (compareAndSetState(state, nextState)) {
                    return nextState == 0;
                }
            }
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return getState()==0?1:-1;
        }
    }
}
