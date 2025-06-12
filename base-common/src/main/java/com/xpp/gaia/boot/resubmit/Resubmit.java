package com.xpp.gaia.boot.resubmit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/**
 * 防重复提交注解
 *
 * @author Akira
 * @since 2021/11/17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Resubmit {

    /**
     * 防重过期时间(默认毫秒)
     *
     * @return
     */
    int expire() default 1000;

    /**
     * 时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 排除字段
     *
     * @return
     */
    String exclude() default "";

    /**
     * 排除字段
     *
     * @return
     */
    String[] excludes() default {};

    /**
     * 报错内容
     *
     * @return
     */
    String message() default "重复提交";

    /**
     * 错误码(默认BAD_REQUEST)
     *
     * @return
     */
    String errorCode() default "400";
}
