package com.xpp.gaia;

import com.xpp.gaia.GaiaImportSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 启用Gaia相关配置
 *
 * @author Akira
 * @since 2021/11/5
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({
        GaiaImportSelector.class
})
public @interface EnableGaia {

    /**
     * 排除默认指定
     *
     * @return the array of 'excludes'
     */
    Class<?>[] excludes() default {};

    /**
     * 启用所有plugIn
     *
     * @return whether use 'enablePlugInAll'
     */
    boolean enablePlugInAll() default true;

    /**
     * 指定plugIn优先级高于enablePlugInAll，意味着"enablePlugInAll=true"条件失效
     *
     * @return the array of 'includePlugIns'
     */
    Class<?>[] includePlugIns() default {};
}
