package com.ss.application.service;

import com.ss.application.remote.ServicePassengerUserClient;
import com.ss.internalcommon.dto.PassengerUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.dto.TokenResult;
import com.ss.internalcommon.request.VerificationCodeDTO;
import com.ss.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ljy.s
 * @Date: 2023/3/28 - 03 - 28 - 11:37
 */

/**
 * 查询乘客用户信息——业务逻辑
 */
@Service
@Slf4j
public class UserService {

    @Resource
    ServicePassengerUserClient servicePassengerUserClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken：" + accessToken);
        // 解析accessToken，拿到手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();
        log.info("手机号：" + phone);

        // 根据手机号查询用户信息
        ResponseResult<PassengerUser> userByPhone = servicePassengerUserClient.getUserByPhone(phone);

        return ResponseResult.success(userByPhone.getData());
    }

}
