package com.ss.internalcommon.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 17:06
 */

/**
 * 验证码提示常量类
 */
public enum CommonStatusEnum {

    /**
     * 验证码错误提示：1000-1099
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),

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
