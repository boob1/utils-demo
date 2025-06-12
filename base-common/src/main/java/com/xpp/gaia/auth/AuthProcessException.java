package com.xpp.gaia.auth;

import com.xpp.gaia.toolkit.action.ActionProcessException;
import lombok.Getter;

/**
 * TODO
 *
 * @author Akira
 * @since 2022/3/10
 */
public class AuthProcessException extends ActionProcessException {

    @Getter
    public String code = "401";

    public static final String AuthControlOff = "权限未被启用，请配置xpp.auth.authz=true";
    public static final String UnloginWhenAuth = "开启了数据权限控制，但是用户未登录";
    public static final String IllegalAuthWrapper = "权限为空，AuthWrapper可能未被正确的赋值";
    public static final String IllegalAuthParam = "权限参数为空，请检查传值是否正确";
    public static final String IllegalDataScope = "权限范围未指定，或者权限参数与范围不匹配";
    public static final String AuthParamNeedAutowired = "权限参数为空且未采用autowired";

    public AuthProcessException(String message) {
        super(message);
    }

    public AuthProcessException(String code, String message) {
        super(code, message);
    }

    public AuthProcessException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public AuthProcessException(String code, Throwable cause) {
        super(code, cause);
    }
}
