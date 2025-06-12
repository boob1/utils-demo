package com.xpp.gaia.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.apache.ibatis.type.JdbcType;

/**
 * 自定义配置
 *
 * @author Akira
 * @since 2022/1/18
 */
public class MybatisPlusCustomizers implements ConfigurationCustomizer {

    @Override
    public void customize(MybatisConfiguration configuration) {
        configuration.setJdbcTypeForNull(JdbcType.NULL);
    }
}
