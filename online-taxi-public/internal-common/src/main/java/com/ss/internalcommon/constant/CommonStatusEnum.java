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
     * 司机和车辆：1500-1599
     */
    DRIVER_CAR_BIND_NOT_EXITS(1500, "司机和车辆绑定关系不存在"),

    DRIVER_CAR_BIND_EXITS(1501, "司机和车辆绑定关系已存在，请勿重复绑定"),

    DRIVER_NOT_EXITS(1502, "司机不存在"),

    DRIVER_BIND_EXITS(1503, "司机已经被绑定了，请勿重复绑定"),

    CAR_BIND_EXITS(1504, "车辆已经被绑定了，请勿重复绑定"),

    /**
     * 地图信息：1400-1499
     */
    MAP_DISTRICT_ERROR(1400, "地图请求错误"),

    /**
     * 计价规则：1300-1399
     */
    PRICE_RULE_EMPTY(1300, "计价规则不存在"),

    PRICE_RULE_EXISTS(1301, "计价规则已存在，不允许添加"),

    PRICE_RULE_NOT_EDIT(1302, "计价规则没有变化"),

    PRICE_RULE_CHANGED(1303, "计价规则有变化"),

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
     * 订单：1600-1699
     */
    ORDER_GOING_ON(1600, "有正在进行的订单"),

    /**
     * 下单异常
     */
    DEVICE_IS_BLACK(1601, "该设备超过下单次数"),

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
