package com.tencent.singleNodeRateLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description
 * @date 2025-12-08 21:31
 */
public class GuavaLimiter {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 平滑突发限流
         * 平滑突发限流顾名思义，就是允许突发的流量进入，后面再慢慢的平稳限流
         */
        //创建容量为5的桶，并且每秒新增5个令牌，即每200ms新增一个令牌
        /*
        RateLimiter rateLimiter=RateLimiter.create(5);
        while (true) {
            //获取令牌（可以指定一次获取的个数），获取可以执行后续的业务逻辑
            System.out.println(rateLimiter.acquire());
        }
        */


        /**
         * 平滑预热限流
         * 平滑突发限流有可能瞬间带来了很大的流量，如果系统扛不住的话，很容易造成系统挂起，这时候，平滑预热限流便可以解决这个问题
         */
        RateLimiter limiter=RateLimiter.create(5,1000,TimeUnit.MILLISECONDS);
        for (int i = 0; i < 5; i++) {
            System.out.println(limiter.acquire());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 50; i++) {
            System.out.println(limiter.acquire());
        }
    }
}
