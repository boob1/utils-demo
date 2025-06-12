package com.xpp.gaia.auth;

import static com.xpp.gaia.auth.AuthProcessException.IllegalAuthParam;
import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;

import com.xpp.gaia.auth.bean.AuthRequestParam;
import javax.annotation.PostConstruct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Auth包装类
 *
 * @author Akira
 * @since 2022/3/4
 */
@Builder
public class AuthWrapper {
    /**
     * 每次request的权限默认不开启，需要在对应的方法前@Auth开启
     */
    @Getter
    @Setter
    private Boolean requetPermissionOn = false;
    /**
     * 数据权限请求参数
     */
    @Getter
    @Setter
    private AuthRequestParam authRequestParam;

    /**
     * 数据访问限定类型
     */
    @Getter
    private DataScope dataScope;

    @Getter
    private Integer deepIn = 0;

    @PostConstruct
    public void predictDataScope() {
        if (dataScope != null) {
            return;
        }
        // 当指定的DataScope为空时，进行推测； 优先级是STORE->EMP->DEALER->ORG
        if (isNotEmpty(authRequestParam.getOrgArr())) {
            dataScope = DataScope.ORG;
        }
        if (isNotEmpty(authRequestParam.getDealerArr())) {
            dataScope = DataScope.DEALER;
        }
        if (isNotEmpty(authRequestParam.getEmpArr())) {
            dataScope = DataScope.EMP;
        }
        if (isNotEmpty(authRequestParam.getStoreArr())) {
            dataScope = DataScope.STORE;
        }
    }

    public void validateRequestParam() throws AuthProcessException {
        if (!requetPermissionOn) {
            return;
        }
        assertCheck(this.authRequestParam != null, new AuthProcessException(IllegalAuthParam));
        assertCheck(isNotEmpty(this.authRequestParam.getOrgArr())
                        || isNotEmpty(this.authRequestParam.getDealerArr())
                        || isNotEmpty(this.authRequestParam.getEmpArr())
                        || isNotEmpty(this.authRequestParam.getStoreArr()),
                new AuthProcessException(IllegalAuthParam));
    }

    public void setDataScope(DataScope dataScope) {
        if (this.dataScope == null) {
            this.dataScope = dataScope;
            return;
        }
        // 只允许深度扩展，收缩时不处理
        this.deepIn = dataScope.getDeep() - this.dataScope.getDeep();
        if (this.deepIn < 0) {
            this.deepIn = 0;
        }
        this.dataScope = dataScope;
    }

    public boolean matchDataScopeAndParam() {
        if ((this.dataScope == DataScope.ORG && isEmpty(this.authRequestParam.getOrgArr()))
                || (this.dataScope == DataScope.DEALER && isEmpty(this.authRequestParam.getDealerArr()))
                || (this.dataScope == DataScope.EMP && isEmpty(this.authRequestParam.getEmpArr()))
                || (this.dataScope == DataScope.STORE && isEmpty(this.authRequestParam.getStoreArr()))) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public DataScope getDataScopeInOriginRequest() {
        return this.dataScope.getDataScopeByDeep(this.dataScope.getDeep() - this.deepIn);
    }

    public Object[] getArgs() {
        if (this.authRequestParam == null) {
            return null;
        }
        DataScope tmpDs = this.getDataScopeInOriginRequest();
        switch (tmpDs) {
            case STORE:
                return this.getAuthRequestParam().getStoreArr();
            case EMP:
                return this.getAuthRequestParam().getEmpArr();
            case DEALER:
                return this.getAuthRequestParam().getDealerArr();
            case ORG:
                return this.getAuthRequestParam().getOrgArr();
            default:
                return null;
        }
    }

    public Object[] getArgsByDataScope() {
        return this.getArgsByDataScope(this.dataScope);
    }

    public Object[] getArgsByDataScope(DataScope dataScope) {
        if (this.authRequestParam == null) {
            return null;
        }
        DataScope ds = dataScope == null ? this.dataScope : dataScope;
        switch (ds) {
            case STORE:
                return this.getAuthRequestParam().getStoreArr();
            case EMP:
                return this.getAuthRequestParam().getEmpArr();
            case DEALER:
                return this.getAuthRequestParam().getDealerArr();
            case ORG:
                return this.getAuthRequestParam().getOrgArr();
            default:
                return null;
        }
    }

}
