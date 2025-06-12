package com.xpp.gaia.boot.hook;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 启用Hook
 *
 * @author Akira
 * @since 2021/9/29
 */
public class EnableHook implements ImportSelector {

    /**
     * 注入
     *
     * @param annotationMetadata
     * @return
     * @apiNote 自启动，annotationMetadata无效
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                StartHook.class.getName()
        };
    }
}
