package com.xpp.gaia.boot.global;

import static com.xpp.gaia.boot.global.LoadScene.UN_KNOW;

import com.xpp.gaia.boot.hook.StartHook;
import com.xpp.gaia.toolkit.IdWorker;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


/**
 * 全局前置拦截器
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    public static final String VAR_GLOBAL_TRACE_ID = "X-GLOBAL-TRACE-ID";
    public static final String VAR_ACCESS_TOKEN = "X-ACCESS-TOKEN";
    public static final String COMMON_REQUEST_ATTR_LOADER = "_LOADER";
    public static final String GLOBAL_LESS_PATH = ".*((.css)|(.js)|(images)|(swagger)|(anon)).*";

    public static final String LOG_SYMBOL_IN = "|￣￣￣￣￣￣￣￣￣￣￣";
    public static final String LOG_SYMBOL_STEP = "|―";
    public static final String LOG_SYMBOL_OFF = "|＿＿＿＿＿＿＿＿＿＿＿";

    public static ThreadLocal<String> traceVar = new InheritableThreadLocal<>();
    public static ThreadLocal<String> tokenVar = new InheritableThreadLocal<>();
    public static ThreadLocal<String> loadVar = new InheritableThreadLocal<>();


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String path = request.getServletPath();
        if (path.matches(GLOBAL_LESS_PATH)) {
            log.info("{} GLOBAL_LESS_PATH: [{}]", LOG_SYMBOL_STEP, path);
            return true;
        }
        if (this.defendUnSafe(response, handler)) {
            log.info("{} GLOBAL_UNSAFE_PATH: [{}]", LOG_SYMBOL_STEP, path);
            return false;
        }
        String traceId = request.getHeader(VAR_GLOBAL_TRACE_ID);
        if (traceId == null) {
            traceId = this.createTraceId();
        }
        traceVar.set(traceId);
        Thread.currentThread().setName(traceId);
        log.info("{} 全局TRACE-ID: [{}] START", LOG_SYMBOL_IN, traceId);
        log.info("{} Request Path: [{}]", LOG_SYMBOL_STEP, path);

        boolean hasToken = this.prepareToken(request);
        if (!hasToken) {
            // 默认来自SFA
            request.setAttribute(COMMON_REQUEST_ATTR_LOADER, UN_KNOW);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) {
        clearGlobalVar();
    }

    protected static void clearGlobalVar() {
        if (tokenVar.get() != null) {
            log.debug("{} 用户访问令牌：[{}] FINISH", LOG_SYMBOL_OFF, tokenVar.get());
            tokenVar.remove();
        }
        if (traceVar.get() != null) {
            log.info("{} 全局TRACE-ID：[{}] FINISH", LOG_SYMBOL_OFF, traceVar.get());
            traceVar.remove();
        }
        if (loadVar.get() != null) {
            loadVar.remove();
        }
    }

    protected Boolean defendUnSafe(HttpServletResponse response,
                                   Object handler) {
        if (handler instanceof ResourceHttpRequestHandler) {
            try {
                response.getWriter().print("please check your url!");
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    protected boolean prepareToken(HttpServletRequest request) {
        String accessToken = request.getHeader(VAR_ACCESS_TOKEN);
        if (accessToken == null) {
            return false;
        }
        tokenVar.set(accessToken);
        log.info("{} 用户访问令牌: [{}]", LOG_SYMBOL_STEP, accessToken);
        LoadScene loadScene = LoadScene.getSceneFromToken(accessToken.replace("UAC_", ""));
        request.setAttribute(COMMON_REQUEST_ATTR_LOADER, loadScene.toString());
        loadVar.set(loadScene.toString());
        return true;
    }

    private String createTraceId() {
        IdWorker idWorker = new IdWorker(1, 1, 1);
        return this.getProfilePrefix() + idWorker.nextId();
    }

    private String getProfilePrefix() {
        String profile = StartHook.profile == null ? "X" : StartHook.profile.toLowerCase();
        if (profile.matches(".*dev.*")
                || profile.matches(".*local.*")) {
            return "D";
        } else if (profile.matches(".*test.*")
                || profile.matches(".*daily.*")) {
            return "T";
        } else if (profile.matches(".*uat.*")
                || profile.matches(".*stage.*")) {
            return "U";
        } else if (profile.matches(".*prod.*")
                || profile.matches(".*real.*")) {
            return "P";
        } else {
            return profile;
        }
    }
}
