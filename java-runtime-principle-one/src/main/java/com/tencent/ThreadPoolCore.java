package com.tencent;

import java.sql.SQLOutput;
import java.util.concurrent.*;

/**
 * @author 观自在
 * @date 2025-11-19 22:58
 *
 *
 * 计算型任务：cpu数量的1-2倍
 * IO型任务：相对比计算型任务，需要多一些线程，要根据具体的IO阻塞时长进行考量决定。如tomcat默认最大线程数：200
 * 也可以考虑根据需要在一个最小数量和最大数量间自动增减线程数
 */
public class ThreadPoolCore {


    /**
     * 1、线程池信息，核心线程数量5，最大数量10，无界队列，超出核心线程数量的线程存活时间：5秒，指定拒绝策略的
     * 队列没有满的情况下，只会去丢到队列。队列满了的情况下，才会去创建线程
     */

    private void threadPoolExecutorTest() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        testCommon(threadPoolExecutor);

    }

    /**
     * 2、线程池信息，核心线程数量5，最大数量10。队列大小3，超出核心线程数量的线程存活时间：5秒，指定拒绝策略的
     */
    private void threadPoolExecutorTest2() throws Exception {
//        创建一个 核心线程数量为5，最大数量为10，等待队列最大是3的线程池，也就是最大容纳13个任务
//        默认的策略是抛出RejectedExecutionException异常，java.util.concurrent.ThreadPoolExecutor.AbortPolicy
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("有任务被拒绝了");
            }
        });
        testCommon(threadPoolExecutor);
    }


    /**
     * 3、线程池信息：核心线程数量5，最大数量5，无界队列，超出核心线程数量的线程存活时间：5秒
     */
    private void threadPoolExecutorTest3() throws Exception {
//        和Executor.newFixedThreadPool(int nThreads) 一样的
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        testCommon(threadPoolExecutor);
    }

    /**
     * 4、线程池信息：核心线程数量0，最大数量Integer.MAX_VALUE，SynchronousQueue队列，超出核心线程数量的线程存活时间：60秒
     */
    private void threadPoolExecutorTest4() throws Exception {
//        SynchronousQueue，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间，与其他队列不同的是，它维护一组线程，
//        这些线程在等待着把元素加入或移除队列
//        在使用SynchronousQueue作为工作队列的前提下，客户端代码向线程池提交任务时，
//        而线程池中又没有空闲的线程能够从SynchronousQueue队列实例中取一个任务，
//        那么相应的offer方法调用就会失败（即任务没有被存入工作队列）
//        此时，ThreadPoolExecutor会新建一个新的工作者线程用于对这个入队列失败的任务进行处理（假设此时线程池的大小还未达到其最大
//        线程池大小maximumPoolSize）。
//        和Executors.newCachedThreadPool()一样的
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        testCommon(threadPoolExecutor);

        Thread.sleep(60000L);
        System.out.println("60秒后，再看线程池中数量："+threadPoolExecutor.getPoolSize());
    }

    /**
     * 5、定时执行线程池信息：3秒后执行，一次性任务，到点就执行<br/>
     * 核心线程数量5，最大数量Integer.MAX_VALUE，DelayedWorkQueue延时队列，超出核心线程数量的线程存活时间：0秒
     */
    private void threadPoolExecutorTest5() throws Exception {
//        和Executors.newScheduledThreadPool()一样的
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPoolExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务被执行，现在时间："+System.currentTimeMillis());
            }
        },3000,TimeUnit.MILLISECONDS);
        System.out.println("定时任务，提交成功，时间是："+System.currentTimeMillis()+"，当前线程池中线程数量："+
                scheduledThreadPoolExecutor.getPoolSize());
    }

    /**
     * 6、定时执行线程池信息：线程固定数量5，<br/>
     * 核心线程数量5，最大数量Integer.MAX_VALUE，DelayedWorkQueue延时队列，超出核心线程数量的线程存活时间：0秒
     */
    private void threadPoolExecutorTest6() throws Exception {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
//     如果超时直接执行
        threadPoolExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("任务-1被执行，现在时间："+System.currentTimeMillis());
            }
        },2000,1000,TimeUnit.MILLISECONDS);

//     等到上次执行完再计时，不管上次执行超不超时
        threadPoolExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("任务-2被执行，现在时间："+System.currentTimeMillis());
            }
        },2000,1000,TimeUnit.MILLISECONDS);
    }

    /**
     * 7、终止线程，线程池信息：核心线程数量5，最大数量10，队列大小3，超出核心线程数量的线程存活时间：5秒，指定拒绝策略的
     */
    private void threadPoolExecutorTest7() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.err.println("有任务被拒绝执行了");
            }
        });
//        测试：提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 0; i < 15; i++) {
            int n=i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("开始执行："+n);
                        Thread.sleep(3000L);
                        System.err.println("执行结束："+n);
                    }catch (Exception e){
                        System.out.println("异常："+e.getMessage());
                    }
                }
            });
            System.out.println("任务提交成功："+i);
        }

//        1秒后终止线程池
        Thread.sleep(1000L);
        threadPoolExecutor.shutdown();
//        再次提交提示失败
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("追加一个任务 "+System.currentTimeMillis());
            }
        });
    }

    /**
     * 测试：提交15个执行时间需要3秒的任务，看线程池的状况
     *
     * @param threadPoolExecutor 传入不同的线程池，看不同的结果
     */
    public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
//        测试：提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 0; i < 15; i++) {
            int n=i;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("开始执行："+n);
                        Thread.sleep(3000L);
                        System.err.println("执行结束："+n);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("任务提交成功："+i);
        }
//        查看线程数量，查看队列等待数量
        Thread.sleep(500L);
        System.out.println("当前线程池线程数量为："+threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为："+threadPoolExecutor.getQueue().size());
//        等待15秒，查看线程数量和队列数量（理论上，会被超出核心线程数量的线程自动销毁
        Thread.sleep(15000L);
        System.out.println("当前线程池的数量为："+threadPoolExecutor.getPoolSize());
        System.out.println("当前线程池等待的数量为："+threadPoolExecutor.getQueue().size());
    }
}
