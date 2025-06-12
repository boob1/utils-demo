package com.xpp.gaia.boot.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 启用Global
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
public class EnableGlobal implements ImportSelector {

    static String[] defaultImports = {
            GlobalConfiguration.class.getName(),
            GlobalExceptionHandler.class.getName()
    };

    /**
     * 注入
     *
     * @param annotationMetadata 注解类元数据
     * @return 注入类名
     * @apiNote 自启动，annotationMetadata无效
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return defaultImports;
    }
}
