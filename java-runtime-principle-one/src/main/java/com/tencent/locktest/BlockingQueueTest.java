package com.tencent.locktest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 观自在
 * @description
 * @date 2025-12-01 22:59
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue BlockingQueue = new BlockingQueue(5);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    BlockingQueue.put("" + i);
                }
            }
        }).start();

        Thread.sleep(1000L);

        for (int i = 0; i < 10; i++) {
            BlockingQueue.take();
            Thread.sleep(1000L);
        }
    }
}


class BlockingQueue{
    //容器
    List<Object> list=new ArrayList<>();
    private Lock lock=new ReentrantLock();
    private Condition putCondition=lock.newCondition();
    private Condition takeCondition=lock.newCondition();
    private int length=0;



    public BlockingQueue(int length){
        this.length=length;
    }

    public void put(Object o) {
        lock.lock();
        try {
            while(true){
                if(list.size()<length){
                    list.add(o);
                    System.out.println("队列中放入元素："+o);
                    takeCondition.signal();
                    return;
                }else { //满的，放不进了
                    putCondition.await();//挂起
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Object take()  {
        lock.lock();
        try {
            if(list.size()>0){
                Object o=list.remove(0);
                System.out.println("队列中取得元素："+o);
                putCondition.signal();
                return o;
            }else{
                takeCondition.await();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }
}