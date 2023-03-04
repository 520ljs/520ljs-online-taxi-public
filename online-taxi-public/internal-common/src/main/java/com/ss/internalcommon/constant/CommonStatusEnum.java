package com.ss.internalcommon.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 17:06
 */
public enum CommonStatusEnum {

    /**
     * 成功
     */
    SUCCESS(1, "success"),
    /**
     * 失败
     */
    FAIL(0, "fail");

    @Getter
    private int code;
    @Getter
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
