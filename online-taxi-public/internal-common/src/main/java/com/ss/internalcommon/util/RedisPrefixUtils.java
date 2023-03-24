package com.ss.internalcommon.util;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/8 - 03 - 08 - 16:43
 */
public class RedisPrefixUtils {

    // 乘客验证码的前缀
    public static String verificationCodePrefix = "passenger-verification-code-";

    // token存储的前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号，生成key
     *
     * @param passengerPhone
     * @return
     */
    public static String generatorKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 前缀 加上 手机号-身份标识，生成token
     *
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone, String identity, String tokenType) {
        return tokenPrefix + phone + "-" + identity + "-" + tokenType;
    }

}
