package com.tencent.CPUFasterTest;

/**
 * @author 观自在
 * @description
 * @date 2025-12-10 13:24
 */
public class DeadLockTest {

    public static final String obj1 = "obj1";
    public static final String obj2 = "obj2";

    public static void main(String[] args) {
        //处理用户请求时，出现了死锁。用户无响应，多次重试，大量资源被占用()
        Thread thread1 = new Thread(new Lock1());
        Thread thread2 = new Thread(new Lock2());
        thread1.start();
        thread2.start();
    }
}

class Lock1 implements Runnable {
    public void run() {
        try {
            System.out.println("Lock1 running");
            while (true) {
                synchronized (DeadLockTest.obj1) {
                    System.out.println("Lock1 locked obj1");
                    Thread.sleep(3000); //获取obj1后先等一会儿，让lock2有足够的时间锁住obj2
                    synchronized (DeadLockTest.obj2) {
                        System.out.println("Lock1 locked obj2");
                    }
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Lock2 implements Runnable {
    public void run() {
        try {
            System.out.println("Lock1 running");
            while (true) {
                synchronized (DeadLockTest.obj2) {
                    System.out.println("Lock2 locked obj2");
                    Thread.sleep(3000); //获取obj1后先等一会儿，让lock2有足够的时间锁住obj2
                    synchronized (DeadLockTest.obj1) {
                        System.out.println("Lock2 locked obj1");
                    }
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
