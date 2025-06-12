package com.xpp.gaia.boot.measure;

import static com.xpp.gaia.boot.global.GlobalInterceptor.COMMON_REQUEST_ATTR_LOADER;

import com.xpp.gaia.boot.global.GlobalInterceptor;
import com.xpp.gaia.boot.global.LoadScene;
import com.xpp.gaia.boot.hook.StartHook;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Measure拦截器
 *
 * @author Akira
 * @since 2024/2/22
 */
@Slf4j
public class MeasureInterceptor implements HandlerInterceptor {

    protected final String MEASUREMENT = "MeasureX";

    public static final String MEASURE_MAP_KEY_META = "META";
    public static final String MEASURE_MAP_KEY_ERROR = "ERROR";
    public static final String COMMON_REQUEST_ATTR_ACCOUNT = "_ACCOUNT";

    public static final String SFA_PW_KEY = "1oR8uIM2O5qv65l2"; // 加密解密标识

    public static ThreadLocal<Map<String, String>> measureVar = ThreadLocal.withInitial(() -> {
        Map<String, String> dataMap = new HashMap<>(2);
        dataMap.put(MEASURE_MAP_KEY_META, "_EMPTY");
        dataMap.put(MEASURE_MAP_KEY_ERROR, "0");
        return dataMap;
    });

    protected static ExpiringMap<String, String> tokenCache = ExpiringMap.builder()
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    private static final Base64.Decoder DECODE_64 = Base64.getDecoder();

    @Autowired(required = false)
    InfluxDB influxDB;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("_startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在Controller方法处理完毕后，但在视图渲染之前执行的逻辑
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求处理完成后执行的逻辑
        long startTime = (long) request.getAttribute("_startTime");
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        String loader = LoadScene.UN_KNOW.toString();
        String token = "EMPTY";
        String account = "UN_AUTH";
        if (request.getAttribute(COMMON_REQUEST_ATTR_LOADER) != null) {
            loader = request.getAttribute(COMMON_REQUEST_ATTR_LOADER).toString();
        }
        if (GlobalInterceptor.tokenVar != null && GlobalInterceptor.tokenVar.get() != null) {
            token = GlobalInterceptor.tokenVar.get();
        }
        if (token != null && !token.equals("EMPTY")) {
            Object obj = request.getAttribute(COMMON_REQUEST_ATTR_ACCOUNT);
            if (obj != null) {
                account = obj.toString();
            }
            // account = this.getAccount(token);
        }
        String path = request.getRequestURI().replaceAll("//", "/");
        this.pointIn(loader, token, account, path, elapsedTime);
        measureVar.remove();
    }

    protected void pointIn(String loader, String token, String account, String path, long cost) {
        String profile = StartHook.profile == null ? "X" : StartHook.profile.toLowerCase();
        // 只在生产环境使用
        if (!profile.matches(".*prod.*") && !profile.matches(".*real.*")) {
            log.debug("Gaia Measure在测试环境未被启动");
            return;
        }
        Point point = Point.measurement(MEASUREMENT)
                .tag("loader", loader)
                .tag("server", StartHook.appName)
                .tag("account", account)
                .tag("meta", measureVar.get().get(MEASURE_MAP_KEY_META))
                .tag("path", path)
                .addField("token", token)
                .addField("cost", cost)
                .addField("error", measureVar.get().get(MEASURE_MAP_KEY_ERROR))
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        influxDB.write(point);
    }

    private String getAccount(String token) {
        String account = tokenCache.get(token);
        if (account == null) {
            try {
                account = this.sfaPwdDecrypt(token, SFA_PW_KEY);
            } catch (Exception e) {
                log.error(e.getMessage());
                account = "UN_DECY";
            }
            tokenCache.put(token, account);
        }
        return account;
    }

    private String sfaPwdDecrypt(String pwd, String decryptKey) throws Exception {
        byte[] encryptBytes = DECODE_64.decode(pwd);
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        String account = new String(decryptBytes);
        account = account.replaceAll("_SANQUAN_BOZHI.*", "");
        return account;
    }

}
