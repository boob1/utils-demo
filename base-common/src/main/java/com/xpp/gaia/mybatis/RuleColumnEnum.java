package com.xpp.gaia.mybatis;

/**
 * 约定字段
 *
 * @author Akira
 * @since 2022/4/29
 */
public enum RuleColumnEnum {

    CREATE_TIME("createTime"),
    CREATOR("creator"),
    CREATOR_ORG("creatorOrg"),
    CREATOR_POS("creatorPos"),
    CREATOR_RANK("creatorRank"),
    UPDATE_TIME("updateTime"),
    UPDATER("updater"),
    UPDATER_ORG("updaterOrg");

    private String property;

    RuleColumnEnum(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
