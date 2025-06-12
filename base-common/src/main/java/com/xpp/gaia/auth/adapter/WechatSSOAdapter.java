package com.xpp.gaia.auth.adapter;

import com.xpp.gaia.boot.utils.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 * 提供redis单例
 *
 *
 * @author Akira
 * @since 2022/2/15
 */
public class WechatSSOAdapter {

    private static RedisTemplate<String, String> redisService;

    private static RedisTemplate<String, String> getRedisService() {
        if (redisService == null) {
            redisService = (RedisTemplate<String, String>) SpringUtil.getBean("redisTemplate");
        }
        return redisService;
    }

//    public static AuthUser fetchAuthUser(String openId) {
//        String key = "LOGIN_SUCCESS_" + openId;
//        String val = getRedisService().opsForValue().get(key);
//        if (val == null) {
//            return null;
//        }
//        Gson gson = new Gson();
//        UserInfoVO userInfoVO = gson.fromJson(val, UserInfoVO.class);
//        AuthUser user = new AuthUser();
//        if (userInfoVO.getUserType().equals("经销商业代")) {
//            user.setUType(UserType.DEALER.getType());
//            user.setId(userInfoVO.getId().toString());
//        } else {
//            user.setUType(UserType.EMPLOYEE.getType());
//            user.setSfaUserId(userInfoVO.getSfaUserId());
//            try {
//                user.setOrgId(Integer.parseInt(userInfoVO.getOrgId()));
//            } catch (Exception e) {
//                throw new AuthProcessException("用户所属组织信息存在异常，组织信息变更后请重新登录再尝试");
//            }
//        }
//        user.setAccount(userInfoVO.getAccount());
//        user.setUserName(userInfoVO.getRealName());
//        user.setMobile(userInfoVO.getMobile());
//        user.setDealerUsers(userInfoVO.getDealerUsers());
//        user.setHasAllPermission(false);
//        user.setUserType(userInfoVO.getUserType());
//        user.setPositionId(userInfoVO.getPositionId());
//        user.setPosition(userInfoVO.getPositionCode());
//        user.setPositionName(userInfoVO.getPositionName());
//        user.setPositionRank(userInfoVO.getPositionLevel());
//        user.setPosCodeRule(userInfoVO.getPosCodeRule());
//        user.setOrgCodeRule(userInfoVO.getOrgCodeRule());
//        user.setOrgCode(userInfoVO.getOrgId());
//        user.setOrgName(userInfoVO.getOrgName());
//        user.setStatus("0");
//        user.setLoginSource("WECHAT");
//        return user;
//    }
}
