package com.xpp.gaia.auth.anno;

import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;

import com.xpp.gaia.auth.Auth;
import com.xpp.gaia.auth.AuthDataAPIs;
import com.xpp.gaia.auth.AuthProcessException;
import com.xpp.gaia.auth.AuthUser;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

/**
 * 员工工号Advice
 *
 * @author Akira
 * @since 2023/9/12
 */
@Aspect
@Configuration
@Slf4j
public class XppValidAdvice {

    @Autowired(required = false)
    AuthDataAPIs authDataAPIs;

    @Pointcut("@annotation(XppValid)")
    public void pointCut() {
    }

    @Around("pointCut()&&@annotation(xppValid)")
    public Object around(ProceedingJoinPoint joinPoint, XppValid xppValid) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Annotation[][] parameterAnnos = signature.getMethod().getParameterAnnotations();
        Parameter[] parameters = signature.getMethod().getParameters();
        Object[] args = joinPoint.getArgs();
        // 针对注解在参数上的处理
        for (int i = 0; i < parameterAnnos.length; i++) {
            for (Annotation anno : parameterAnnos[i]) {
                if (anno instanceof StaffId) {
                    Class clazz = args[i].getClass();
                    if (!clazz.isPrimitive() && clazz != String.class) {
                        throw new AuthProcessException("StaffId不能注解封装对象");
                    }
                    Object newVal = this.handleStaffAnno(xppValid.autoWired(), args[i]);
                    args[i] = newVal;
                    break;
                }
            }
        }
        // 针对注解在对象内某个字段的处理
        loop:
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterClazz = parameter.getType();
            // 基本类型的参数（例如Integer、String）的处理方式
            if (parameterClazz.isPrimitive() || parameterClazz == String.class) {
                continue;
            }
            Field[] fields = parameterClazz.getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annos = field.getDeclaredAnnotations();
                for (Annotation anno : annos) {
                    if (anno instanceof StaffId) {
                        field.setAccessible(true);
                        if (field.getType() == String.class) {
                            Object value = field.get(args[i]);
                            Object newVal = this.handleStaffAnno(xppValid.autoWired(), value);
                            field.set(args[i], newVal);
                        } else if (List.class.isAssignableFrom(field.getType())) {
                            Object value = field.get(args[i]);
                            List<String> list = (List<String>) value;
                            assertCheck(!CollectionUtils.isEmpty(list), "注解字段数据为空");
                            List<String> newList = list.stream().map(e -> {
                                Object obj = this.handleStaffAnno(false, e);
                                if (obj == null) {
                                    return "_NIL";
                                }
                                return obj.toString();
                            }).collect(Collectors.toList());
                            field.set(args[i], newList);
                        } else {
                            throw new ActionProcessException("注解不支持当前字段类型");
                        }
                        break loop;
                    }
                }
            }
        }
        return joinPoint.proceed(args);
    }

    private Object handleStaffAnno(Boolean autoWired, Object val) {
        assertCheck(val != null, "工号不能为空");
        ActionResult<AuthUser> userResult = authDataAPIs.empSearch(val.toString());
        assertCheck(userResult != null, "无效的工号: [" + val + "]");
        assertCheck(userResult.isSuccess() && userResult.getData() != null, "无效的工号: [" + val + "]");
        if (autoWired) {
            Auth.setUser(userResult.getData());
            return userResult.getData().getAccount();
        }
        return userResult.getData().getAccount();
    }
}
