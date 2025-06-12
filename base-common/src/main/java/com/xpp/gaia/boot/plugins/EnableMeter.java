package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.boot.measure.MeasureConfiguration;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用Meter
 *
 * @author Akira
 * @since 2024/2/22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        MeasureConfiguration.class
})
public @interface EnableMeter {
}
