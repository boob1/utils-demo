package com.xpp.gaia.auth.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

/**
 * 可重复读的HttpRequest包装类
 *
 * @author Akira
 * @since 2022/3/5
 */
@Slf4j
public class RereadableRequestWrapper extends HttpServletRequestWrapper {

    private String body;

    private String popValue;

    protected RereadableRequestWrapper(HttpServletRequest request, @NonNull String popKey) throws IOException {
        super(request);
        String bodyData = StreamUtils.copyToString(request.getInputStream(), Charset.forName(request.getCharacterEncoding()));
        if (StringUtils.isBlank(bodyData)) {
            this.body = bodyData;
            return;
        }
        JsonElement jsonElement = JsonParser.parseString(bodyData);
        if (jsonElement instanceof JsonArray) {
            this.body = bodyData;
            return;
        }
        try {
            if (((JsonObject) jsonElement).size() > 1) {
                for (Map.Entry<String, JsonElement> each : ((JsonObject) jsonElement).entrySet()) {
                    if (each.getKey().equalsIgnoreCase(popKey)) {
                        this.popValue = each.getValue().toString();
                        ((JsonObject) jsonElement).remove(popKey);
                        break;
                    }
                }
                // json字段数如果==1时，需要剔除外部字段名
                if (((JsonObject) jsonElement).size() > 1) {
                    this.body = jsonElement.toString();
                    return;
                }
                String tmp = null;
                for (Map.Entry<String, JsonElement> each : ((JsonObject) jsonElement).entrySet()) {
                    tmp = each.getValue().toString();
                    break;
                }
                this.body = tmp;
            } else {
                this.body = bodyData;
            }
        } catch (Exception e) {
            log.warn("request body解析异常，无法识别权限参数");
            this.body = bodyData;
        }
    }

    public String getBody() {
        return this.body;
    }

    public String getPopedValue() {
        return this.popValue;
    }


    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.body.getBytes());
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


}
