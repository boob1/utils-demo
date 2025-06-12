package com.xpp.gaia.mybatisplus;

import com.baomidou.mybatisplus.generator.config.converts.select.BranchBuilder;
import com.baomidou.mybatisplus.generator.config.converts.select.Selector;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * 该注册器负责注册并查询类型注册器
 *
 * @author Akira
 * @since 2021/10/28
 */
public class TypeConvertsExt {

    /**
     * 使用指定参数构建一个选择器
     *
     * @param param 参数
     * @return 返回选择器
     */
    static Selector<String, IColumnType> use(String param) {
        return new Selector<>(param.toLowerCase());
    }

    /**
     * 这个分支构建器用于构建用于支持 String.contains(CharSequence) 的分支
     *
     * @param value 分支的值
     * @return 返回分支构建器
     * @see com.baomidou.mybatisplus.generator.config.converts.TypeConverts#contains(CharSequence value)
     */
    static BranchBuilder<String, IColumnType> contains(CharSequence value) {
        return BranchBuilder.of(s -> s.contains(value));
    }

    /**
     * 这个分支构建器用于构建用于支持 String.contains(CharSequence) 的分支
     *
     * @param values 分支的值
     * @return 返回分支构建器
     * @see com.baomidou.mybatisplus.generator.config.converts.TypeConverts#containsAny(CharSequence... values)
     */
    static BranchBuilder<String, IColumnType> containsAny(CharSequence... values) {
        return BranchBuilder.of(s -> {
            for (CharSequence value : values) {
                if (s.contains(value)) return true;
            }
            return false;
        });
    }
}
