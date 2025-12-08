package com.tencent.singleNodeRateLimit;

import java.util.concurrent.*;

/**
 * @author 观自在
 * @description
 * @date 2025-12-08 21:55
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 4, 5,
                TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

        //向线程池里面扔任务
        for (int i = 0; i < 10; i++) {
            System.out.println("当前线程池大小["+threadPool.getPoolSize()+"]，当前队列大小["+queue.size()+"]");

            threadPool.execute(new LllThread("Thread"+i));
        }
        //关闭线程池
        threadPool.shutdown();
    }

    static class LllThread implements Runnable {
        private String name;
        public LllThread(String name) {this.name=name;}

        @Override
        public void run() {
            //做点事情
            try {
                Thread.sleep(1000);
                System.out.println(name+" finished job！");
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
