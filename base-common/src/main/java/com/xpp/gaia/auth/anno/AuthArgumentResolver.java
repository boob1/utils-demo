package com.xpp.gaia.auth.anno;

import static com.xpp.gaia.auth.AuthProcessException.AuthControlOff;
import static com.xpp.gaia.auth.web.AuthFilter.AUTH_WRAPPER_HOLDER;

import com.xpp.gaia.auth.AuthConfiguration;
import com.xpp.gaia.auth.AuthProcessException;
import com.xpp.gaia.auth.AuthWrapper;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 鉴权参数处理拦截器
 *
 * @author Akira
 * @since 2022/3/4
 */
@Slf4j
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        if (!AuthConfiguration.izAuthzOn) {
            throw new AuthProcessException(AuthControlOff);
        }
        Class<?> parameterType = parameter.getParameterType();
        if (parameterType != AuthRequestParam.class) {
            throw new ActionProcessException(ActionResult.CODE_ILLEGAL_ARGUMENT, "期望的对象类型为: AuthRequestParam");
        }
        // 注意，这里不能使用Auth.volidAndGetRequestAuth方式，因为DataScope还没有被加入到Wrapper中去
        AuthWrapper wrapper = AUTH_WRAPPER_HOLDER.get();
        return wrapper == null ? null : wrapper.getAuthRequestParam();
    }

}
