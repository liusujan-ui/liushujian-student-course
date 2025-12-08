package com.tencent.singleNodeRateLimit;

/**
 * @author 观自在
 * @description
 * @date 2025-12-08 21:09
 */
public class LeakerBucketLimiter {

    //时间刻度
    private static long timeStamp = System.currentTimeMillis();
    //桶大小
    private static int bucketSize = 10;
    //每ms流出的请求
    private static int rate=1;
    //当前的水量
    private static long count = 0;

    public static boolean grant(){
        long now = System.currentTimeMillis();

        //计算出水的数量
        long out=(now-timeStamp)*rate;
        //先执行漏水，计算剩余水量
        count=Math.max(0,count-out);
        timeStamp=now;
        if ((count+1)<bucketSize){
            //先执行漏水，计算剩余水量
            count++;
            return true;
        }else {
            //水满拒绝加水
            return false;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                if (grant()){
                    System.out.println("执行业务逻辑");
                }else {
                    System.out.println("限流");
                }
            }).start();
        }
    }
}
