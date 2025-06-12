package com.xpp.gaia.auth.bean;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * TODO
 *
 * @author Akira
 * @since 2022/3/17
 */
@Data
public class AuthDataCommonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限类型：1组织 2经销商 3下级业代 4门店")
    private String authorityType;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("业代姓名")
    private String fullName;

    @ApiModelProperty("门店时：照片")
    private String photo;

    @ApiModelProperty("门店时：地址")
    private String address;
}
