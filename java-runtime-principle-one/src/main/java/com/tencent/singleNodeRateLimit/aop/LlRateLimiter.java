package com.tencent.singleNodeRateLimit.aop;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description
 * @date 2025-12-09 22:49
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LlRateLimiter {

    //以固定的数值往令牌桶添加令牌
    double permitsPerSecond() default 1.0;

    //获取令牌最大的等待时间
    long timeOut() default 0;

    //时间单位（例如：分、秒、毫秒）
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    //没有获取到令牌时 默认的返回提示信息，默认值可以修改
    String msg() default "系统繁忙，请稍后再试！";
}
