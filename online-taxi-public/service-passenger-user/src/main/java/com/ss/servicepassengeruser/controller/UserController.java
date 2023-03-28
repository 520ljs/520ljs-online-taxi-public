package com.ss.servicepassengeruser.controller;

import com.auth0.jwt.interfaces.Verification;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.VerificationCodeDTO;
import com.ss.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:33
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号："+passengerPhone);
        return userService.loginOrRegister(passengerPhone);

    }

    @GetMapping("/user")
    public ResponseResult getUser(@RequestBody VerificationCodeDTO verificationCodeDTO) {

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.getUserByPhone(passengerPhone);
    }

}
