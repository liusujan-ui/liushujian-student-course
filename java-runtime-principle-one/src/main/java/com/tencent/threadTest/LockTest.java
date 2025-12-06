package com.tencent.threadTest;

import java.util.concurrent.locks.LockSupport;

/**
 * if语句 来判断，是否进入等待状态是错误的，应该在循环中检查等待，处于等待的线程可能会收到错误警报和伪唤醒
 *
 * @author 观自在
 * @date 2025-11-18 21:20
 *
 * 生产包子 <----> 消费包子
 *
 * 线程-1去买包子，没有包子，则不执行。线程-2生产出包子，通知线程-1继续执行
 */
public class LockTest {
    public static void main(String[] args) throws Exception {
        LockTest lockTest = new LockTest();
        lockTest.suspendResumeTest();
        lockTest.suspendResumeDeadLockTest();
        lockTest.suspendResumeDeadLockTest2();
        lockTest.waitNotifyTest();
        lockTest.waitNotifyDeadLockTest();
        lockTest.parkUnparkTest();
        lockTest.parkUnparkDeadLockTest();
    }

//    包子店
    public static Object baozidian=null;

    /**
     *
     *
     *  正常的suspend/resume
     * @throws Exception
     */
    public void suspendResumeTest() throws Exception{
//        启动线程
        Thread consumerThread=new Thread(()->{
            if(baozidian==null){
                System.out.println("1、没有包子，进入等待");
                Thread.currentThread().suspend();
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
        consumerThread.resume();
        System.out.println("3、通知消费者");
    }


    /* 死锁的suspend/resume。suspend并不会像wait一样释放锁，故此容易写出死锁代码。suspend挂起之后并不会释放锁，故此容易写出死锁代码 */
    public void suspendResumeDeadLockTest() throws Exception{
//        启动线程
        Thread consumerThread=new Thread(()->{
                System.out.println("1、进入等待");
//                当前线程拿到锁，然后挂起
                synchronized (this){
                    while (baozidian==null){  //如果没有包子则进入等待
                    Thread.currentThread().suspend();
                }
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
//        争取到锁以后，再恢复consumerThread
        synchronized (this){
            consumerThread.resume();
        }
        System.out.println("3、通知消费者");
    }

    /* 导致程序永久挂起的suspend/resume。先执行suspend，再执行resume。反之则死锁*/
    public void suspendResumeDeadLockTest2() throws Exception{
//        启动线程
        Thread consumerThread=new Thread(()->{
            if(baozidian==null){
                System.out.println("1、没有包子，进入等待");
                try { // 为这个线程加上一点延时
                    Thread.sleep(5000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                这里的挂起执行在resume后面
                Thread.currentThread().suspend();
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
        consumerThread.resume();
        System.out.println("3、通知消费者");
        consumerThread.join();
    }

    /* 正常的wait/notify */
    public void waitNotifyTest() throws Exception{
//        启动线程
        new Thread(()->{
                synchronized (this){
                    while (baozidian==null){
                    try {
                        System.out.println("1、进入等待");
                        this.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2、买到包子，回家");
        }).start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
        synchronized (this){
            this.notifyAll();
            System.out.println("3、通知消费者");
        }
    }

    /* 会导致永久等待的wait/notify。wait/notify需要注意先后顺序  */
    public void waitNotifyDeadLockTest() throws Exception{
//        启动线程
        new Thread(()->{
                try {
                    Thread.sleep(5000L);
                }catch (Exception e){
                    e.printStackTrace();
                }
                synchronized (this){
                    while (baozidian==null){ //如果没有包子，则进入等待
                        try {
                            System.out.println("1、进入等待");
                            this.wait();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }
            }
            System.out.println("2、买到包子，回家");
        }).start();
//        3秒之后，生产了一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
        synchronized (this){
            this.notifyAll();
            System.out.println("3、通知消费者");
        }
    }

    /*
     * park和unpark，提供许可令牌，不会要求顺序。多次调用unpark之后，再调用park，线程会直接运行，但不会叠加
     */

    /* 正常的park/unpark */
    public void parkUnparkTest() throws Exception{
//        启动线程
        Thread consumerThread=new Thread(()->{
            if(baozidian==null){ // 如果没有包子，则进入等待
                System.out.println("1、进入等待");
                LockSupport.park();
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
        LockSupport.unpark(consumerThread);
        System.out.println("3、通知消费者");
    }

    /* 死锁的park和unpark。park/unpark 不会释放锁，在同步代码块中容易死锁 */
    public void parkUnparkDeadLockTest() throws Exception{
        Thread consumerThread=new Thread(()->{
            System.out.println("1、进入等待");
//                当前线程拿到锁，然后挂起
            synchronized (this){
                while(baozidian==null){ //如果没有包子，则进入等待
                    LockSupport.park();
                }
            }
            System.out.println("2、买到包子，回家");
        });
        consumerThread.start();
//        3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian=new Object();
//        争取到锁以后，再恢复consumerThread
        synchronized (this){
            LockSupport.unpark(consumerThread);
        }
        System.out.println("3、通知消费者");
    }

}
