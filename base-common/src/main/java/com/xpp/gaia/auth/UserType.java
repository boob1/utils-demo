package com.xpp.gaia.auth;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户类型(大类)
 *
 * @author Akira
 * @since 2022/8/8
 */
public enum UserType {
    EMPLOYEE("E", "我司员工"),
    DEALER("D", "经销商员工"),
    TERMINAL("T", "门店");

    private String type;
    private String name;

    UserType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String getThisUserType(AuthUser user) {
        if (!StringUtils.isBlank(user.getAccount()) && !StringUtils.isBlank(user.getPosition())) {
            if (user.getPosition().startsWith(user.getAccount() + "X")) {
                return DEALER.type;
            }
        }
        return EMPLOYEE.type;
    }

    public static UserType getThisUserType(String type) {
        if (StringUtils.isBlank(type)) {
            throw new AuthProcessException("未指定用户类型参数");
        }
        UserType[] ut = UserType.values();
        for (UserType t : ut) {
            if (t.type.equals(type)) {
                return t;
            }
        }
        throw new AuthProcessException("用户类型参数不存在");
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
