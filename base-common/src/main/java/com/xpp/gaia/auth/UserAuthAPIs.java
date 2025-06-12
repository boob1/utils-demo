package com.xpp.gaia.auth;

import com.xpp.gaia.toolkit.ActionResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户认证授权API
 *
 * @author Akira
 * @since 2024/5/29
 */
@FeignClient(
        name = "xpp-auth",
        path = "/userAuth",
        contextId = "UserAuthAPI"
)
public interface UserAuthAPIs {

    @PostMapping("validAndGetAuth")
    @ApiOperation(value = "令牌鉴权")
    ActionResult<AuthUser> validAndGetAuth(@RequestParam(name = "token", required = false) String token,
                                           @RequestParam(name = "openId", required = false) String openId);

    @PostMapping("v2/validAndGetAuth")
    @ApiOperation(value = "令牌鉴权")
    ActionResult<AuthUser> validAndGetAuthV2(@RequestParam(name = "token", required = true) String token);
}
