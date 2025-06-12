package com.xpp.gaia.boot.trace;

import static com.xpp.gaia.boot.global.GlobalInterceptor.LOG_SYMBOL_STEP;

import com.xpp.gaia.boot.utils.JoinPointUtil;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.lang.reflect.Method;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * Param日志 Aspect
 *
 * @author Akira
 * @since 2021/9/6
 */
@Aspect
@Component
@Slf4j
public class ParamLogAdvice {

    public ParamLogAdvice() {
        log.info("Initializing Gaia ParamLogAdvice");
    }

    /**
     * 1、父类有注解，但子类没有注解的话，@within和@target是不会对子类生效的。
     * 2、子类没有注解的情况下，只有没有被重写的有注解的父类的方法才能被@within匹配到。
     * 3、如果父类无注解，子类有注解的话，@target对父类所有方法生效，@within只对重载过的方法生效。
     */
    @Pointcut("(@within(com.xpp.gaia.boot.trace.ParamLog) || @annotation(ParamLog))" +
            "|| (@within(com.xpp.gaia.boot.trace.Trace) || @annotation(Trace))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Map<String, Object> paramMap = JoinPointUtil.getParamsFromJoinPoint(joinPoint);
        String str = JsonUtil.toJson(paramMap);
        log.info("{} [{}]参数: {}", LOG_SYMBOL_STEP, JoinPointUtil.getMethodPath(joinPoint), str);
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long b = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long e = System.currentTimeMillis();
            long costed = e - b;
            log.info("{} [{}]的耗时: {}(ms)", LOG_SYMBOL_STEP, this.getMethodPath(joinPoint), costed);
        }
    }

    /**
     * 获取调用方法路径
     *
     * @param joinPoint
     * @return
     */
    protected String getMethodPath(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}
