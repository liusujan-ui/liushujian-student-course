package com.tencent.singleNodeRateLimit.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 观自在
 * @description
 * @date 2025-12-09 23:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    private Integer code;
    private String msg;
}
