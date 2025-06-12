package com.xpp.gaia.mybatisplus;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.BLOB;
import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.BYTE_ARRAY;
import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.FLOAT;
import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.STRING;
import static com.xpp.gaia.mybatisplus.TypeConvertsExt.contains;
import static com.xpp.gaia.mybatisplus.TypeConvertsExt.containsAny;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * 该注册器负责注册并查询H2 Oracle-Mode类型注册器
 *
 * @author Akira
 * @since 2021/10/28
 */
public class H2OracleModeTypeConvert extends OracleTypeConvert {

    /**
     * 将对应的类型名称转换为对应的java类类型
     * 这里修复了mp中内存型h2时对于number型匹配的问题，因为都会返回BigDecimal
     *
     * @param typeName 类型名称
     * @return 返回列类型
     * @see OracleTypeConvert#toNumberType(String typeName)
     */
    public static IColumnType toNumberType(String typeName) {
        if (typeName.matches("number\\([0-9]\\)(.*)")) {
            return DbColumnType.INTEGER;
        } else if (typeName.matches("number\\(1[0-8]\\)(.*)")) {
            return DbColumnType.LONG;
        }
        return DbColumnType.BIG_DECIMAL;
    }

    /**
     * 处理类型转换
     * Params:
     * config – 全局配置
     * fieldType – 字段类型
     * Returns:
     * 返回的对应的列类型
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConvertsExt.use(fieldType)
                .test(containsAny("char", "clob").then(STRING))
                .test(containsAny("date", "timestamp").then(p -> toDateType(config)))
                .test(contains("number").then(H2OracleModeTypeConvert::toNumberType))
                .test(contains("numeric").then(H2OracleModeTypeConvert::toNumberType))
                .test(contains("float").then(FLOAT))
                .test(contains("blob").then(BLOB))
                .test(containsAny("binary", "raw").then(BYTE_ARRAY))
                .or(STRING);
    }

}
