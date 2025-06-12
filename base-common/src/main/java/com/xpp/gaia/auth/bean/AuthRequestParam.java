package com.xpp.gaia.auth.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据权限请求参数
 *
 * @author Akira
 * @since 2022/3/4
 */
@Getter
@Setter
public class AuthRequestParam {

    private Integer[] orgArr;
    private String[] dealerArr;
    private String[] empArr;
    private String[] storeArr;
    private String keyWords;
    private String positionId;
    private String posCodeRule;
    private String positionRank;
    private String[] orgCodeRules;
    private Boolean ignoreRoleHasAll;

}
