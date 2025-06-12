package com.xpp.gaia.auth.anno;

import static com.xpp.gaia.auth.AuthProcessException.AuthParamNeedAutowired;

import com.xpp.gaia.auth.Auth;
import com.xpp.gaia.auth.AuthProcessException;
import com.xpp.gaia.auth.AuthWrapper;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

/**
 * Authz Aspect
 *
 * @author Akira
 * @since 2022/3/7
 */
@Aspect
@Configuration
@Slf4j
public class AuthAdvice {

    @Pointcut("@annotation(Authz)")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(enableAuthz)")
    public Object around(ProceedingJoinPoint joinPoint, Authz enableAuthz) throws Throwable {
        AuthWrapper authWrapper = Auth.volidAndGetRequestAuth();
        // 开启Auth注解就会进入权限控制
        authWrapper.setRequetPermissionOn(true);
        authWrapper.setDataScope(enableAuthz.dataScope());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();

        // 对于字段注解的判断
        Annotation[][] parameterAnnos = signature.getMethod().getParameterAnnotations();
        for (int i = 0; i < parameterAnnos.length; i++) {
            for (Annotation anno : parameterAnnos[i]) {
                if (anno instanceof AuthParam) {
                    BeanUtils.copyProperties(authWrapper.getAuthRequestParam(), args[i]);
                    break;
                }
            }
        }
        // 对于AuthRequestParam声明的处理
        Parameter[] parameters = signature.getMethod().getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterClazz = parameter.getType();
            // 基本类型的参数（例如Integer、String）的处理方式
            if (parameterClazz.isPrimitive() || parameterClazz == String.class) {
                continue;
            }
            if (parameterClazz == AuthRequestParam.class) {
                Object val = args[i];
                if (val != null) {
                    break;
                }
                // 入参为null且autoWired时，需要分AuthWrapper对象或AuthWrapper子类对象处理
                if (!enableAuthz.autoWired()) {
                    throw new AuthProcessException(AuthParamNeedAutowired);
                }
                if (parameterClazz == AuthRequestParam.class) {
                    args[i] = authWrapper.getAuthRequestParam();
                    break;
                }
            }
        }
        return joinPoint.proceed(args);
    }
}
