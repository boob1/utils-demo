package com.xpp.gaia.auth.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DealerBean {

    @ApiModelProperty("经销商编码")
    private String dealerCode;

    @ApiModelProperty("经销商名称")
    private String dealerName;
}
