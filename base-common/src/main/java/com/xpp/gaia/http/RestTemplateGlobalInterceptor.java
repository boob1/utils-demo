package com.xpp.gaia.http;

import static com.xpp.gaia.boot.global.GlobalInterceptor.VAR_ACCESS_TOKEN;
import static com.xpp.gaia.boot.global.GlobalInterceptor.VAR_GLOBAL_TRACE_ID;
import static com.xpp.gaia.boot.global.GlobalInterceptor.tokenVar;
import static com.xpp.gaia.boot.global.GlobalInterceptor.traceVar;

import java.io.IOException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * RestTemplate全局拦截器
 *
 * @author Akira
 * @since 2021/11/21
 */
@Component
public class RestTemplateGlobalInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        if (traceVar.get() != null) {
            request.getHeaders().set(VAR_GLOBAL_TRACE_ID, traceVar.get());
        }
        if (tokenVar.get() != null) {
            request.getHeaders().set(VAR_ACCESS_TOKEN, tokenVar.get());
        }
        if (request.getMethod() == HttpMethod.POST && request.getHeaders().getContentType() == null) {
            request.getHeaders().set("Content-Type", "application/json;charset=utf-8");
        }
        return execution.execute(request, body);
    }
}
