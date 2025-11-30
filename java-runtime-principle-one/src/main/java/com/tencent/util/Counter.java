package com.tencent.util;

import lombok.Data;

/**
 * @author 观自在
 * @description
 * @date 2025-11-30 13:21
 */

@Data
public class Counter {

    private  int num=0;

    public Counter() {}

    public void add() {
        num++;
    }

}
