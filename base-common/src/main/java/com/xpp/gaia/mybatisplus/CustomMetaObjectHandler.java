package com.xpp.gaia.mybatisplus;

import static com.xpp.gaia.mybatis.RuleColumnEnum.CREATE_TIME;
import static com.xpp.gaia.mybatis.RuleColumnEnum.CREATOR;
import static com.xpp.gaia.mybatis.RuleColumnEnum.CREATOR_ORG;
import static com.xpp.gaia.mybatis.RuleColumnEnum.CREATOR_POS;
import static com.xpp.gaia.mybatis.RuleColumnEnum.CREATOR_RANK;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATER;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATER_ORG;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATE_TIME;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xpp.gaia.auth.Auth;
import com.xpp.gaia.auth.AuthUser;
import java.util.Date;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

/**
 * Mybatis-Plus 自动填充处理
 *
 * @author Akira
 * @since 2021/12/23
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            fillValue(metaObject, CREATE_TIME.getProperty(), () -> new Date());
            fillValue(metaObject, UPDATE_TIME.getProperty(), () -> new Date());
            AuthUser user = Auth.getUser();
            if (user != null) {
                if (containsField(metaObject, CREATOR.getProperty())) {
                    fillValue(metaObject, CREATOR.getProperty(), () -> user.getAccount());
                }
                if (containsField(metaObject, CREATOR_ORG.getProperty())) {
                    fillValue(metaObject, CREATOR_ORG.getProperty(), () -> user.getOrgId());
                }
                if (containsField(metaObject, CREATOR_POS.getProperty())) {
                    fillValue(metaObject, CREATOR_POS.getProperty(), () -> user.getPosition());
                }
                if (containsField(metaObject, CREATOR_RANK.getProperty())) {
                    fillValue(metaObject, CREATOR_RANK.getProperty(), () -> user.getPositionRank());
                }
                if (containsField(metaObject, UPDATER.getProperty())) {
                    fillValue(metaObject, UPDATER.getProperty(), () -> user.getAccount());
                }
                if (containsField(metaObject, UPDATER_ORG.getProperty())) {
                    fillValue(metaObject, UPDATER_ORG.getProperty(), () -> user.getOrgId());
                }
            }
        } catch (ReflectionException re) {
            re.printStackTrace();
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            fillUpdateTime(metaObject, UPDATE_TIME.getProperty());
            AuthUser user = Auth.getUser();
            if (user != null) {
                if (containsField(metaObject, UPDATER.getProperty())) {
                    fillValue(metaObject, UPDATER.getProperty(), () -> user.getAccount());
                }
                if (containsField(metaObject, UPDATER_ORG.getProperty())) {
                    fillValue(metaObject, UPDATER_ORG.getProperty(), () -> user.getOrgId());
                }
            }
        } catch (ReflectionException re) {
            re.printStackTrace();
        }
    }

    private void fillUpdateTime(MetaObject metaObject, String fieldName) {
        if (!metaObject.hasGetter(fieldName)) {
            return;
        }
        setFieldValByName(fieldName, new Date(), metaObject);
    }

    private void fillValue(MetaObject metaObject, String fieldName, Supplier<Object> valueSupplier) {
        if (!metaObject.hasGetter(fieldName)) {
            return;
        }
        Object sidObj = metaObject.getValue(fieldName);
        if (sidObj == null && metaObject.hasSetter(fieldName) && valueSupplier != null) {
            setFieldValByName(fieldName, valueSupplier.get(), metaObject);
        }
    }

    private Boolean containsField(MetaObject metaObject, String fieldName) {
        return metaObject.hasGetter(fieldName);
    }
}
