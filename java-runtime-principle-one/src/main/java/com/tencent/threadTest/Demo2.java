package com.tencent.threadTest;

/**
 * @author 观自在
 * @date 2025-11-17 21:06
 * @description 线程的不同状态之间切换
 *              new、runnable、block、waiting、time waiting、terminated共计六种线程状态
 *              代码展示了不同线程状态之间的切换。。。
 */
public class Demo2 {
    public static Thread thread1;
    public static Demo2 obj;

    public static void main(String[] args) throws Exception {
//        第一种状态切换  新建 -> 运行 -> 终止
        System.out.println("#####第一种状态切换 - 新建->运行->终止######");
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1当前状态：" + Thread.currentThread().getState().toString());
                System.out.println("thread1执行了");
            }
        });
        System.out.println("没调用start方法，thread1当前状态："+thread1.getState());
        thread1.start();
        Thread.sleep(2000L);//等待thread1执行结束，再看状态
        System.out.println("等待两秒，再看thread1当前状态："+thread1.getState());

        System.out.println();
        System.out.println("#####第二种，新建->运行->等待->运行->终止（sleep方式）#####");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2当前状态：" + Thread.currentThread().getState().toString());
                System.out.println("thread2执行了");
            }
        });
        System.out.println("没调用start方法，thread2当前状态："+thread2.getState());
        thread2.start();
        System.out.println("调用start方法，thread2当前状态："+thread2.getState());
        Thread.sleep(200L);
        System.out.println("等待200毫秒，再看thread2当前状态："+thread2.getState());
        Thread.sleep(3000L);
        System.out.println("等待3秒，再看thread2当前状态："+thread2.getState());


        System.out.println();
        System.out.println("#####第三种，新建->运行->阻塞->运行->终止#####");
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Demo2.class) {
                    System.out.println("thread3当前状态：" + Thread.currentThread().getState().toString());
                    System.out.println("thread3执行了");
                }
            }
        });
        synchronized (Demo2.class) {
            System.out.println("没调用start方法，thread3当前状态："+thread3.getState());
            thread3.start();
            System.out.println("调用start方法，thread3当前状态"+thread3.getState());
            Thread.sleep(200L);//等待200毫秒，再看状态
            System.out.println("等待200毫秒，再看thread3当前状态："+thread3.getState());
        }
        Thread.sleep(3000L);
        System.out.println("等待3秒，让thread3抢到锁，再看thread3当前状态："+thread3.getState());
    }
}
