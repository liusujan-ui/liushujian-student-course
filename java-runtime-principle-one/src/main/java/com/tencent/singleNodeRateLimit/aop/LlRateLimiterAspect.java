package com.tencent.singleNodeRateLimit.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.tencent.singleNodeRateLimit.controller.ResponseResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 观自在
 * @description
 * @date 2025-12-09 22:57
 */
@Aspect
@Component
public class LlRateLimiterAspect {
    private Logger logger = LoggerFactory.getLogger(LlRateLimiterAspect.class);


    //使用url作为key，存放令牌桶，防止每次重新创建令牌桶
    private Map<String,RateLimiter> limiterMap = Maps.newConcurrentMap();

    @Pointcut("@annotation(com.tencent.singleNodeRateLimit.aop.LlRateLimiter)")
    public void llRateLimiter(){
    }

    @Around("llRateLimiter()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取request和response
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

        String uri = request.getRequestURI();

        //获取自定义注解
        LlRateLimiter rateLimiter= getLlRateLimiter(joinPoint);

        if (rateLimiter!=null){
            RateLimiter limiter=null;

            //判断map集合中是否有创建好的令牌桶
            if (!limiterMap.containsKey(uri)){
                //创建令牌桶
                limiter=RateLimiter.create(rateLimiter.permitsPerSecond());
                limiterMap.put(uri,limiter);
                logger.info("*************>> 请求{}，创建令牌桶，容量{}，成功！",uri,rateLimiter.permitsPerSecond());
            }
            limiter=limiterMap.get(uri);
            //500 毫秒内没有获取到令牌，就直接放弃获取进行服务降级处理
            boolean tryAcquire = limiter.tryAcquire(rateLimiter.timeOut(), rateLimiter.timeUnit());

            if (!tryAcquire){
//                response.setContentType("text/html;charset=utf-8");
                //封装http response 响应
                responseResult(response,500,rateLimiter.msg());
                return null;
            }
        }
        return joinPoint.proceed();
    }


    /**
     * 自定义响应结果
     * @param response 响应对象
     * @param code  响应码
     * @param msg  响应信息
     */
    private void responseResult(HttpServletResponse response, Integer code, String msg) {
        response.resetBuffer();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        PrintWriter writer=null;
        try {
            writer=response.getWriter();
            ResponseResult result = new ResponseResult(code, msg);
            String json = JSONObject.toJSONString(result);
            writer.println(json);
            response.flushBuffer();
        }catch (Exception e){
            logger.error("相应出错，错误信息：{}",e.getMessage(),e);
        }finally {
            if (writer!=null){
                writer.flush();
                writer.close();
            }
        }
    }


    private LlRateLimiter getLlRateLimiter(final JoinPoint joinPoint){
        Method[] methods = joinPoint.getTarget().getClass().getDeclaredMethods();
        String name= joinPoint.getSignature().getName();
        if (!StringUtils.isEmpty(name)){
            for (Method method : methods) {
                LlRateLimiter annotation = method.getAnnotation(LlRateLimiter.class);
                if (!Objects.isNull(annotation)&&name.equals(method.getName())){
                    return annotation;
                }
            }
        }
        return null;
    }
}
