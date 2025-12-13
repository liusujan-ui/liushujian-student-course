package com.tencent.springAopUtil.aop;

import org.springframework.stereotype.Component;

/**
 * @author 观自在
 * @description
 * @date 2025-12-13 16:37
 */
@Component
public class LaopService implements Laop{
    @Override
    public String getL() {
        return "163.l";
    }
}
