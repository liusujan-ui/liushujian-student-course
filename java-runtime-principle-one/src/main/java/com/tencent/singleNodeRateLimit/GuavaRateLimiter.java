package com.tencent.singleNodeRateLimit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description 限流神器：Guava RateLimiter
 * Guava RateLimiter基于令牌桶算法，我们只需要告诉RateLimiter系统限制的QPS是多少
 * 那么RateLimiter将以这个速度往桶里面放入令牌，让后请求的时候，通过tryAquire（）方法向RateLimiter获取许可（令牌）
 *
 * @date 2025-12-08 21:18
 */
public class GuavaRateLimiter {

    private static ConcurrentHashMap<String, RateLimiter> resourceRateLimiter = new ConcurrentHashMap<>();

    static {
        createResourceLimiter("order",50);
    }

    public static void createResourceLimiter(String resource,int qps) {
        if (resourceRateLimiter.contains(resource)) {
            resourceRateLimiter.get(resource).setRate(qps);
        }else {
            //每秒50个，2s后达到正常速率
            RateLimiter limiter = RateLimiter.create(qps,1000L, TimeUnit.MILLISECONDS);
            resourceRateLimiter.putIfAbsent(resource, limiter);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <500; i++) {
            new Thread(()->{
                if (resourceRateLimiter.get("order").tryAcquire()) {
                    System.out.println("执行业务逻辑");
                }else {
                    System.out.println("限流");
                }
            }).start();
        }
    }
}
