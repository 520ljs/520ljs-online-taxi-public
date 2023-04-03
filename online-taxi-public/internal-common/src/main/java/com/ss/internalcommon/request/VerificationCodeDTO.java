package com.ss.internalcommon.request;

import lombok.Data;

/**
 * @Author: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:35
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
