package com.xpp.gaia.http;

import java.util.List;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * 自定义RestTemplate拦截器
 *
 * @author Akira
 * @since 2021/11/21
 */
public class CustomRestTemplateInterceptors {

    public List<ClientHttpRequestInterceptor> interceptors;
}
