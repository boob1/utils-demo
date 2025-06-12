package com.xpp.gaia.mybatis;

import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import lombok.Getter;

/**
 * Mybatis执行异常(仅对gaia中的Mybatis插件启用)
 *
 * @author Akira
 * @since 2022/3/13
 */
public class MybatisProcessExcetion extends ActionProcessException {

    @Getter
    public String code = ActionResult.CODE_FAILED;

    public MybatisProcessExcetion(String message) {
        super(message);
    }

    public MybatisProcessExcetion(String code, String message) {
        super(code, message);
    }

    public MybatisProcessExcetion(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public MybatisProcessExcetion(String code, Throwable cause) {
        super(code, cause);
    }
}
