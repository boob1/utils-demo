package com.xpp.gaia.auth.anno;

import com.xpp.gaia.auth.DataScope;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限参数注解
 *
 * @author Akira
 * @since 2022/3/4
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Authz {

    boolean autoWired() default true;

    DataScope dataScope() default DataScope.ORG;

    String[] roles() default {};
}
