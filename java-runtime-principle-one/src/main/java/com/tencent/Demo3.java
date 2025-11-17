package com.tencent;

import com.tencent.util.StopThread;

/**
 * @author 观自在
 * @date 2025-11-17 21:32
 *
 * 理想：i=0,j=0  程序执行结果：i=1,j=0。没有保证同步代码块里面的数据一致性，stop破坏了线程的一致性。
 */
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        StopThread thread = new StopThread();
        thread.start();
//        休眠1秒，确保i变量自增成功
        Thread.sleep(1000);
//        中止线程
//        thread.stop();//错误的终止
        thread.interrupt();//正确的终止
        while (thread.isAlive()){
            //确保线程已经终止
        }//输出结果
        thread.print();
    }
}
