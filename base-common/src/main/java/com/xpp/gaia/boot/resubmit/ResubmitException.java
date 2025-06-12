package com.xpp.gaia.boot.resubmit;

import lombok.Getter;

/**
 * 重复提交异常
 *
 * @author Akira
 * @since 2021/11/19
 */
public class ResubmitException extends RuntimeException {

    @Getter
    public String code;

    @Getter
    public String resubmitData;

    public ResubmitException(String code, String message, String resubmitData) {
        super(message);
        this.code = code;
        this.resubmitData = resubmitData;
    }
}
