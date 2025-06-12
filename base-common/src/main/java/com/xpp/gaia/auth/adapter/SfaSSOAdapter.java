package com.xpp.gaia.auth.adapter;

import com.xpp.gaia.auth.AuthProcessException;
import com.xpp.gaia.auth.AuthUser;
import com.xpp.gaia.auth.UserType;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import lombok.Data;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Sfa SSO适配
 * 你提供的类 SfaSSOAdapter 是一个 单点登录（SSO）适配器，用于从 Redis 中反序列化并解析 SFA 系统的用户信息，
 * 并将其转换为统一的 AuthUser 对象。
 *
 * @author Akira
 * @since 2022/2/15
 */
public class SfaSSOAdapter {

    public static JedisPool authJedisPool;


    /**
     * 通过令牌从redis获取人员对象
     *
     * @param accessToken 令牌
     * @return 人员对象
     */
    public static AuthUser fetchAuthUser(String accessToken) {
        String key = "LOGIN_SUCCESS_" + accessToken;
        Jedis jedis = null;
        byte[] objBytes = null;
        try {
            jedis = authJedisPool.getResource();
            objBytes = jedis.get(key.getBytes(StandardCharsets.UTF_8));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        Object obj = objectDeserialization(objBytes);
        AuthUser user = new AuthUser();
        SSOUser ssoUser = JsonUtil.fromJson(obj.toString(), SSOUser.class);
        user.setId(ssoUser.getId());
        user.setAccount(ssoUser.getUsername());
        user.setStaffCode(ssoUser.getStaffcode());
        user.setUserName(ssoUser.getRealname());
        user.setPositionId(ssoUser.getPosId());
        user.setPosition(StringUtils.hasLength(ssoUser.getPosCode()) ? ssoUser.getPosCode() : "-");
        user.setPositionRank(StringUtils.hasLength(ssoUser.getPosLevel()) ? ssoUser.getPosLevel() : "-");
        user.setPositionName(StringUtils.hasLength(ssoUser.getPosLevel()) ? ssoUser.getPosName() : "");
        try {
            user.setOrgId(Integer.parseInt(ssoUser.getOrgId()));
        } catch (Exception e) {
            throw new AuthProcessException("用户所属组织信息存在异常，组织信息变更后请重新登录再尝试");
        }
        user.setOrgCode(ssoUser.getOrgCode());
        user.setOrgName(ssoUser.getOrgName());
        user.setRoleCodes(ssoUser.getRoleCodes());
        user.setLoginSource(ssoUser.getLoginSource());
        user.setMobile(ssoUser.getPhoneNumber());
        user.setRefCustCode(ssoUser.getCustcode());
        user.setRefCustOrgCode(ssoUser.getCustomerOrgCode());
        user.setPosCodeRule(ssoUser.getPoscoderule());
        user.setOrgCodeRule(ssoUser.getOrgcoderule());
        user.setHasAllPermission(ssoUser.getHasAllPermission() == null ? false : ssoUser.getHasAllPermission());
        user.setUType(UserType.getThisUserType(user));
        user.setSubType(ssoUser.getUsertype().toString());
        user.setUserType("香飘飘员工");
        user.setStatus("0"); //在职
        user.setLoginSource("SFA");
        return user;
    }

    //字符串反序列化为对象
    public static Object objectDeserialization(byte[] serStr) {
        Object newObj = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serStr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            newObj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newObj;
    }

    @Data
    public static class SSOUser implements Serializable {
        protected String id;
        protected String username;//登录账号
        protected String staffcode;//员工编号
        protected Integer usertype;//用户类型
        protected String realname;//真实姓名
        protected String posId; //主职位ID
        protected String posCode;//主职位编码
        protected String posName;// 主职位名称
        protected String posLevel;// 主职位职级
        protected String orgId;//主职位对应组织id
        protected String orgCode;//主职位对应组织code
        protected String roleCodes;// 所有角色编码
        protected String language = "zh_cn";
        protected String loginSource;//登录来源
        //经销商业代对应经销商编码
        protected String custcode;
        protected String poscoderule;
        protected String orgcoderule;
        //客户组织编码
        protected String customerOrgCode;
        protected String ip;
        protected String orgName;//组织名称
        protected String phoneNumber;//手机号
        protected String password;
        protected Boolean hasAllPermission;
    }
}
