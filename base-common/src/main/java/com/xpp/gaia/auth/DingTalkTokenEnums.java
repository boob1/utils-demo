package com.xpp.gaia.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 钉钉秘钥
 * @Author zhaocong
 * @Date 2025/4/18 13:09
 **/
@Getter
@AllArgsConstructor
public enum DingTalkTokenEnums {
    ERROR("SECadf58cd5293fd91dd3cd18bbeb6868031231f80ae3895bbf40163ee5f95fde3f", "e5e52861e3d38448cfc60c7788a36a5408bd72a66cc70717c98d1a1ff1606b23"),
    ;

    private String secret;
    private String customRobotToken;


}
