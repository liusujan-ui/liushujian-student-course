package com.tencent.cas.lockdemo;

/**
 * @author 观自在
 * @description
 * @date 2025-12-01 11:25
 */
public class SyncDemo {

    private static int i=0;
    public static void main(String[] args) {
        SyncDemo syncDemo = new SyncDemo();
        SyncDemo syncDemo1 = new SyncDemo();
        syncDemo1.add();
        syncDemo.add();

        SyncDemo.add2();
        SyncDemo.add3();

        System.out.println(i);


    }


     
    public synchronized void add() {
        i++;
    }

    public void add1(){
        synchronized (this){
            i++;
        }
    }

    //对象本身，等价的
    public static synchronized void add2(){
        i++;
    }

    public static void add3(){
        synchronized (SyncDemo.class){
            i++;
        }
    }




}
