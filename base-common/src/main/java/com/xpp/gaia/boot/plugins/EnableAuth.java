package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.auth.AuthConfiguration;
import com.xpp.gaia.auth.anno.AuthAdvice;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用权限配置
 *
 * @author Akira
 * @since 2021/11/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        AuthConfiguration.class,
        AuthAdvice.class
})
public @interface EnableAuth {
}
