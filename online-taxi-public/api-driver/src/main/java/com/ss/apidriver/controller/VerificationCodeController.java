package com.ss.apidriver.controller;

import com.ss.apidriver.service.VerificationCodeService;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/14 - 04 - 14 - 14:43
 */
@RestController
@Slf4j
public class VerificationCodeController {

    @Resource
    VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String driverPhone = verificationCodeDTO.getDriverPhone();
        log.info("司机的号码：" + driverPhone);
        return verificationCodeService.checkAndsendVerificationCode(driverPhone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String driverPhone = verificationCodeDTO.getDriverPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println("手机号：" + driverPhone + "，" + verificationCode);

        return verificationCodeService.checkCode(driverPhone, verificationCode);
    }
}
