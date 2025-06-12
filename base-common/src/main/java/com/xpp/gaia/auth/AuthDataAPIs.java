package com.xpp.gaia.auth;

import com.github.pagehelper.PageInfo;
import com.xpp.gaia.auth.bean.AuthDataCommonBean;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.auth.bean.OrgNode;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.Pager;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据权限通用接口
 *
 * @author Akira
 * @since 2022/3/17
 */
@FeignClient(
        name = "xpp-auth",
        path = "/dataAuthority",
        contextId = "dataAuthorityAPI"
)
public interface AuthDataAPIs {

    @PostMapping("orgSearch")
    ActionResult<List<OrgNode>> orgSearch(@RequestParam(name = "treeMode", required = false) Boolean treeMode);

    @PostMapping("customerSearch")
    ActionResult<PageInfo<AuthDataCommonBean>> customerSearch(Pager<AuthRequestParam> params);

    @PostMapping("salesmanSearch")
    ActionResult<PageInfo<AuthDataCommonBean>> salesmanSearch(Pager<AuthRequestParam> params);

    @PostMapping("storeSearch")
    ActionResult<PageInfo<AuthDataCommonBean>> storeSearch(Pager<AuthRequestParam> params);

    @GetMapping("extend/empSearch/{empStaff}")
    ActionResult<AuthUser> empSearch(@RequestParam(name = "empStaff", required = true) String empStaff);

    @PostMapping("orgList")
    ActionResult<List<OrgNode>> orgList(@RequestBody OrgNode orgNode);
}
