package com.ss.application.request;

import lombok.Data;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:34
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
