package com.tencent.springAopUtil.annotation;

import com.tencent.springAopUtil.aop.LAopBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author 观自在
 * @description
 * @date 2025-12-13 16:45
 */
@Import(LAopBeanPostProcessor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface L {
}
