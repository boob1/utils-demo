package com.xpp.gaia;

import com.xpp.gaia.EnableGaia;
import com.xpp.gaia.auth.AuthConfiguration;
import com.xpp.gaia.auth.AuthXConfiguration;
import com.xpp.gaia.auth.anno.AuthAdvice;
import com.xpp.gaia.auth.anno.XppValidAdvice;
import com.xpp.gaia.boot.measure.MeasureConfiguration;
import com.xpp.gaia.boot.resubmit.ResubmitAdvice;
import com.xpp.gaia.boot.trace.ParamLogAdvice;
import com.xpp.gaia.boot.trace.TraceAdvice;
import com.xpp.gaia.boot.utils.SpringUtil;
import com.xpp.gaia.doc.ApiDocConfiguration;
import com.xpp.gaia.http.FeignGlobalConfiguration;
import com.xpp.gaia.http.RestTemplateGlobalConfiguration;
import com.xpp.gaia.job.XxlJobConfiguration;
import com.xpp.gaia.mybatis.MybatisInterceptorConfiguration;
import com.xpp.gaia.mybatisplus.MybatisPlusConfiguration;
import com.xpp.gaia.redis.RedisConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * Gaia ImportSelector
 *
 * @author Akira
 * @since 2021/11/23
 */
public class GaiaImportSelector implements ImportSelector {

    static String[] loadClass = {
            ParamLogAdvice.class.getName(),
            TraceAdvice.class.getName(),
            ResubmitAdvice.class.getName(),
            AuthAdvice.class.getName(),
            XppValidAdvice.class.getName(),
            SpringUtil.class.getName()
    };

    static String[] plugInClass = {
            MybatisInterceptorConfiguration.class.getName(),
            MybatisPlusConfiguration.class.getName(),
            FeignGlobalConfiguration.class.getName(),
            RestTemplateGlobalConfiguration.class.getName(),
            RedisConfiguration.class.getName(),
            ApiDocConfiguration.class.getName(),
            XxlJobConfiguration.class.getName(),
            AuthConfiguration.class.getName(),
            AuthXConfiguration.class.getName(),
            MeasureConfiguration.class.getName()
    };

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableGaia.class.getName(), false);
        Assert.notNull(attributes, "'@EnableGaia' won't be null");
        Assert.notNull(attributes.get("excludes"), "'excludes' attribute can not be null");
        Assert.notNull(attributes.get("enablePlugInAll"), "'enablePlugInAll' attribute can not be null");
        Assert.notNull(attributes.get("includePlugIns"), "'includePlugIns' attribute can not be null");
        Class<?>[] excludes = (Class<?>[]) attributes.get("excludes");
        boolean enablePlugInsAll = (boolean) attributes.get("enablePlugInAll");
        Class<?>[] includePlugIns = (Class<?>[]) attributes.get("includePlugIns");
        // 根据条件，装载需要导入的类
        List<String> loads = new ArrayList<>(Arrays.asList(loadClass));
        // 根据条件，装载需要导入的PlugIn类
        if (includePlugIns.length > 0) {
            Arrays.stream(includePlugIns).forEach(e -> loads.add(e.getName()));
        }
        // includePlugIns优先级高于enablePlugInsAll
        if (enablePlugInsAll && includePlugIns.length == 0) {
            loads.addAll(new ArrayList<>(Arrays.asList(plugInClass)));
        }
        Arrays.stream(excludes).forEach(e -> loads.removeIf(val -> val.equals(e.getName())));
        return loads.toArray(new String[0]);
    }
}
