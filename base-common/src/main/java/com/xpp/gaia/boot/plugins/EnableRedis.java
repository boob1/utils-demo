package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.redis.RedisConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用Redis配置
 *
 * @author Akira
 * @since 2021/11/23
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        RedisConfiguration.class,
})
public @interface EnableRedis {
}
