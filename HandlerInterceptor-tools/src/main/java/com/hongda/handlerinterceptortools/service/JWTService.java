package com.hongda.handlerinterceptortools.service;


import com.hongda.handlerinterceptortools.common.RestResult;

/**
 * JWT服务接口
 * @author Administrator
 */
public interface JWTService {
    Boolean validateToken(String token);
    String getUsernameFromToken(String token);
}