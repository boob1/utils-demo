package com.xpp.gaia.boot.resubmit;

import com.xpp.gaia.boot.utils.JoinPointUtil;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * 防重复提交 Aspect
 *
 * @author Akira
 * @since 2021/11/17
 */
@Aspect
@Configuration
@Slf4j
public class ResubmitAdvice {

    static ExpiringMap<String, String> resubmitCache = ExpiringMap.builder()
            .variableExpiration()
            .expirationPolicy(ExpirationPolicy.CREATED)
            .build();

    public ResubmitAdvice() {
        log.info("Initializing Gaia ResubmitAdvice");
    }

    @Pointcut("@annotation(Resubmit)")
    public void pointCut() {
    }

    @Before("pointCut() && @annotation(enableResubmit)")
    public void before(JoinPoint joinPoint, Resubmit enableResubmit) {
        Map<String, Object> paramMap = JoinPointUtil.getParamsFromJoinPoint(joinPoint);
        String paramsJson = JsonUtil.toJson(paramMap);
        for (String s : enableResubmit.excludes()) {
            paramsJson = paramsJson.replaceAll(String.format("\"%s\":\".*?\",", s), "");
            paramsJson = paramsJson.replaceAll(String.format("\"%s\":.*?,", s), "");
        }
        String jp = JoinPointUtil.getMethodPath(joinPoint);
        String key = jp + "0_0" + paramsJson;
        // 同一个接口且相同参数时生效
        if (resubmitCache.containsKey(key)) {
            throw new ResubmitException(
                    enableResubmit.errorCode(),
                    enableResubmit.message(),
                    paramsJson
            );
        }
        resubmitCache.put(key, jp, enableResubmit.expire(), enableResubmit.timeUnit());
    }

}
