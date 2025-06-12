package com.xpp.gaia.auth;

import lombok.Getter;

/**
 * 数据范围枚举
 *
 * @author Akira
 * @since 2022/3/4
 */
public enum DataScope {

    ORG(1, "org"), DEALER(2, "dealer"), EMP(3, "emp"), STORE(4, "store");

    @Getter
    private Integer deep;
    @Getter
    private String dialect;

    DataScope(Integer deep, String dialect) {
        this.deep = deep;
        this.dialect = dialect;
    }

    public static DataScope getDataScopeByDeep(Integer deep) {
        if (deep == null) {
            throw new AuthProcessException("DataScope的深度必须指定");
        }
        DataScope[] ds = DataScope.values();
        for (DataScope d : ds) {
            if (d.deep == deep) {
                return d;
            }
        }
        throw new AuthProcessException("DataScope的深度不合法");
    }

    public static DataScope getDataScopeByDeepIn(DataScope ds, Integer deepIn) {
        if (ds == null || deepIn == null) {
            throw new AuthProcessException("DataScope和深度都必须指定");
        }
        if (ds.getDeep() - deepIn < 0) {
            throw new AuthProcessException("深度异常");
        }
        return getDataScopeByDeep(ds.getDeep() - deepIn);
    }
}
