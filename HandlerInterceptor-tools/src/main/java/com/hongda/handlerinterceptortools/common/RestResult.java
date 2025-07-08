package com.hongda.handlerinterceptortools.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回结果类
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResult<T> {
    private String code;
    private String msg;
    private T data;
}