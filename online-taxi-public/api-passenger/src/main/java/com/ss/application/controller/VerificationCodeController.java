package com.ss.application.controller;

import com.ss.application.request.VerificationCodeDTO;
import com.ss.application.service.VerificationCodeService;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:32
 */
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数：" + passengerPhone);
        return verificationCodeService.generatorCode(passengerPhone);

    }

}
