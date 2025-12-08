package com.tencent.singleNodeRateLimit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 观自在
 * @description 滑动窗口限流
 * 滑动窗口，该窗口同样的key，都是单线程计算
 *
 * 代码实现思路就是定义好分片数量，每个分片都有一个独立的计数器，所有的分片合计为一个数组
 * 当请求来时，按照分片规则，判断请求应该划分到哪个分片中去，要判断是否超过阈值，就将前N个统计值相加，对比定义的阈值即可
 *
 * @date 2025-12-08 20:44
 */
public class SlidingWindowLimiter {
    /**
     * 循环队列，就是装多个窗口用，该数量是windowSize的2倍
     */
    private AtomicInteger[] timeSlices;
    /**
     * 队列的总长度
     */
    private int timeSliceSize;
    /**
     * 每个时间片的时长，以毫秒为单位
     */
    private int timeMillsPerSlice;
    /**
     * 共有多少时间片（即窗口长度）
     */
    private int windowSize;
    /**
     * 在一个完整窗口期内允许通过的最大阈值
     */
    private int threshold;
    /**
     * 该滑窗的起始创建时间，也就是第一个数据
     */
    private long beginTimestamp;
    /**
     * 最后一个数据的时间戳
     */
    private long lastAddTimestamp;

    public SlidingWindowLimiter(int timeMillsPerSlice, int windowSize, int threshold){
        this.timeMillsPerSlice = timeMillsPerSlice;
        this.windowSize = windowSize;
        this.threshold = threshold;
        //保证存储在至少两个window
        this.timeSliceSize=windowSize*2;
        reset();
    }
    //通过修改每个时间片的时间，窗口数量，阈值，来进行测试
    public static void main(String[] args) {
        //1秒一个时间片，窗口共5个
        SlidingWindowLimiter window = new SlidingWindowLimiter(100, 4, 8);
        for (int i = 0; i < 100; i++) {
            System.out.println(window.addCount(2));

            window.print();
            System.out.println("-------------------------");
            try {
                Thread.sleep(102);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化队列，由于此初始化会申请一些内容空间，为了节省空间，延迟初始化
     */
    private void reset(){
        beginTimestamp = System.currentTimeMillis();
        if (timeSlices!=null){
            return;
        }
        //窗口个数
        AtomicInteger[] localTimeSlices=new AtomicInteger[timeSliceSize];
        for (int i = 0; i < timeSliceSize; i++) {
            localTimeSlices[i]=new AtomicInteger(0);
        }
        timeSlices = localTimeSlices;
    }

    private void print() {
        for (AtomicInteger timeSlice : timeSlices) {
            System.out.print(timeSlice+"-");
        }
    }

    /**
     * 计算当前所在的时间片的位置
     */
    private int locationIndex(){
        long now = System.currentTimeMillis();
        //如果当前的key已经超过一整个时间片了，那么就直接初始化就行了，不用去计算了
        if (now-lastAddTimestamp>timeMillsPerSlice*windowSize){
            reset();
        }
        return (int)((now-beginTimestamp)/timeMillsPerSlice)%timeSliceSize;
    }

    /**
     * 增加计数并判断是否超过阈值
     * @param count 增加的数量
     * @return 是否超过阈值
     */
    public boolean addCount(int count) {
        long now = System.currentTimeMillis();
        int index = locationIndex();
        // 更新当前时间片的计数
        timeSlices[index].addAndGet(count);
        lastAddTimestamp = now;

        // 计算当前窗口内的总请求数
        int totalRequests = 0;
        for (int i = 0; i < windowSize; i++) {
            totalRequests += timeSlices[(index + i) % timeSliceSize].get();
        }

        // 判断是否超过阈值
        return totalRequests > threshold;
    }
}
