package com.tencent;

/**
 * 线程封闭
 * @author 观自在
 * @date 2025-11-18 23:51
 */
public class ThreadCloseVal {
    public static ThreadLocal<String> value=new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        ThreadCloseVal t=new ThreadCloseVal();
        t.threadLocalTest();
    }

    public void threadLocalTest() throws Exception{
        value.set("这是主线程设置的123"); // 主线程设置值

        String v=value.get();
        System.out.println("线程1执行之前，主线程取到的值"+v);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String v=value.get();
                System.out.println("线程1取到的值："+v);
//                设置threadLocal
                value.set("这是线程1设置的值456");

                v=value.get();
                System.out.println("重新设置之后，线程1取到的值："+v);
                System.out.println("线程1执行结束");
            }
        }).start();

        Thread.sleep(5000L); //等待所有线程执行结束

        v=value.get();
        System.out.println("线程1执行之后，主线程取到的值："+v);
    }
}
