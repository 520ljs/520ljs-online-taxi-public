package com.ss.internalcommon.constant;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 17:06
 */

/**
 * success or fail 提示常量类
 */
public enum CommonStatusEnum {

    /**
     * 地图信息：1400-1499
     */
    MAP_DISTRICT_ERROR(1400,"地图请求错误"),

    /**
     * 计价规则：1300-1399
     */
    PRICE_RULE_EMPTY(1300,"计价规则不存在"),

    /**
     * 用户提示：1200-1299
     */
    USER_NOTE_EXISTS(1200, "当前用户不存在"),

    /**
     * Token类提示：1100-1199
     */
    TOKEN_ERROR(1199, "token错误"),

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
