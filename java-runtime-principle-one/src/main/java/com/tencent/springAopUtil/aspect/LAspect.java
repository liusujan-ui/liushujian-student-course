package com.tencent.springAopUtil.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 观自在
 * @description
 * @date 2025-12-12 23:04
 */

@Component
@Aspect
public class LAspect {

    @Pointcut("execution(* com.tencent.springAopUtil.service.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before() {
        System.out.println("before..........");
    }
}
