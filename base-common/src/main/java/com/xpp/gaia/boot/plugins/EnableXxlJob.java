package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.job.XxlJobConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用XxlJob配置
 *
 * @author Akira
 * @since 2022/2/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        XxlJobConfiguration.class,
})
public @interface EnableXxlJob {
}
