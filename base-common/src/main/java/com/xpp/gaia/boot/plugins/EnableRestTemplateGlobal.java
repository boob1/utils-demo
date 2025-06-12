package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.http.RestTemplateGlobalConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用Feign全局配置
 *
 * @author Akira
 * @since 2021/11/23
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        RestTemplateGlobalConfiguration.class
})
public @interface EnableRestTemplateGlobal {
}
