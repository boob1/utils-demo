package com.hongda.handlerinterceptortools.controller;

import java.util.Map;

import com.hongda.handlerinterceptortools.common.RestResult;
import com.hongda.handlerinterceptortools.service.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT控制器类
 * @author Administrator
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/jwt")
public class JWTController {

    @Autowired
    private JWTService jwtService;

    /**
     * 验证令牌有效性
     * @param request 请求体，包含token字段
     * @return RestResult 结果
     */
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<Boolean> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        if (token == null || token.isBlank()) {
            return new RestResult<>("000001", "令牌无效", false);
        }
        return jwtService.validateToken(token);
    }

    /**
     * 从令牌中提取用户名
     * @param request 请求体，包含token字段
     * @return RestResult 结果
     */
    @PostMapping(value = "/extract-username", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RestResult<String> getUsernameFromToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        if (token == null || token.isBlank()) {
            return new RestResult<>("000001", "无法提取用户名", null);
        }
        return jwtService.getUsernameFromToken(token);
    }
}