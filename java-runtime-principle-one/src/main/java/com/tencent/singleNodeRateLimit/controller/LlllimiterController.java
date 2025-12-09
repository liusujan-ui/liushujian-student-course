package com.tencent.singleNodeRateLimit.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.tencent.singleNodeRateLimit.aop.LlRateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author 观自在
 * @description
 * @date 2025-12-09 22:33
 */

@RestController
@RequestMapping("limiter")
public class LlllimiterController {
    private Logger logger = LoggerFactory.getLogger(LlllimiterController.class);

    //以1r/s往桶子中放入令牌
    private RateLimiter rateLimiter=RateLimiter.create(1.0);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("index")
    public String indexLimiter() {
        LocalDateTime now = LocalDateTime.now();

        //500 毫秒内没有获取到令牌，就直接放弃获取进行服务降级处理
        boolean tryAcquire = rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            logger.info("Error -- 时间：{}，获取令牌失败。", now.format(formatter));
            return "系统繁忙，请稍后再试。";
        }
        logger.info("Success -- 时间：{}，获取令牌成功。", now.format(formatter));
        return "Success";
    }
    @GetMapping("index2")
    @LlRateLimiter(permitsPerSecond = 1.0,timeOut=500,timeUnit=TimeUnit.MILLISECONDS
            ,msg = "你不要搞事情，现在服务器繁忙！")
    public String indexLimiter2() {

        //业务逻辑，例如：查询订单
        return "Success"+System.currentTimeMillis();
    }
}
