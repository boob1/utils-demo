package com.xpp.gaia.auth;

import com.xpp.gaia.auth.bean.DealerBean;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户
 *
 * @author Akira
 * @since 2021/12/23
 */
@Getter
@Setter
public class AuthUser implements Serializable {


    protected String id; //用户id
    private String sfaUserId;
    protected String account; //登录账号
    protected String staffCode; //工号
    protected String userCode; //用户Code
    protected String userName; //用户名
    protected String positionId; //职位id
    protected String position; //职位
    protected String positionName; //职位名称
    protected String positionRank; //职级
    protected Integer orgId; //所属组织
    protected String orgCode; //所属组织编码
    protected String orgName; //所属组织名称
    protected String roleCodes; //角色编码
    protected String loginSource; //登录来源
    protected String uType; //用户类型(E:我司人员 D:经销商)
    protected String userType; //兼容微信
    protected String subType; //用户子类型
    protected String mobile; //手机号
    protected Boolean hasAllPermission;//是否总部权限
    protected String status;//在职状态
    protected String posCategory;//职位对应品类
    protected String loginOpenId;//登录的OpenId

    /* ----- 以下是关联客户信息 ------ */
    protected String refCustCode;
    protected String refCustOrgCode;
    protected String posCodeRule;
    protected String orgCodeRule;
    protected String dealerCode; //当前经销商
    protected List<DealerBean> userDealers;

}
