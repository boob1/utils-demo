package com.xpp.gaia.boot.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.multipart.MultipartFile;

/**
 * 切点工具类
 *
 * @author Akira
 * @since 2021/11/19
 */
public class JoinPointUtil {

    /**
     * 获取调用方法全路径
     *
     * @param joinPoint 切点
     * @return 类名+方法名全路径
     */
    public static String getMethodPath(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        return method.getDeclaringClass().getName() + "." + method.getName();
    }

    /**
     * 获取调用方法
     *
     * @param joinPoint 切点
     * @return Method
     */
    public static Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    /**
     * 从切点中获取参数
     *
     * @param joinPoint 切点
     * @return 参数Map
     */
    public static Map<String, Object> getParamsFromJoinPoint(JoinPoint joinPoint) {
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        String[] parameterNames = pnd.getParameterNames(getMethod(joinPoint));
        Map<String, Object> paramMap = new HashMap<>(32);
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] instanceof HttpServletResponse
                    || args[i] instanceof HttpServletRequest
                    || args[i] instanceof HttpSession
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }
}
