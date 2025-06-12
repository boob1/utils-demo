package com.xpp.gaia.boot.plugins;

import com.xpp.gaia.boot.trace.ParamLogAdvice;
import com.xpp.gaia.boot.trace.TraceAdvice;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用Trace
 *
 * @author Akira
 * @since 2021/9/29
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        TraceAdvice.class,
        ParamLogAdvice.class
})
public @interface EnableTrace {
}
