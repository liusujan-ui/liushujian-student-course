package com.tencent.singleNodeRateLimit;

/**
 * @author 观自在
 * @description 变量计数器
 * @date 2025-12-08 20:37
 */
public class CounterLimiter {

    private static long timeStamp = System.currentTimeMillis();

    //限制为1秒内，限制在100个请求
    private static long limitCount = 100;
    //时间间隔（毫秒）
    private static long interval = 1000;
    //请求数
    private static long reqCount = 0;

    public static boolean grant(){
        long now = System.currentTimeMillis();
        if (now < interval+timeStamp ) {
            if (reqCount < limitCount) {
                ++reqCount;
                return true;
            } else {
                return false;
            }
        }else {
            timeStamp=System.currentTimeMillis();
            reqCount=0;
            return true;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (grant()) {
                        System.out.println("执行业务逻辑");
                    }else {
                        System.out.println("限流");
                    }
                }
            }).start();
        }
    }
}
