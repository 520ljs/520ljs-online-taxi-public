package com.ss.application.service;

import com.ss.internalcommon.dto.PassengerUser;
import com.ss.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/28 - 03 - 28 - 11:37
 */

/**
 * 查询乘客用户信息——业务逻辑
 */
@Service
@Slf4j
public class UserService {

    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("accessToken" + accessToken);
        // 解析accessToken，拿到手机号

        // 根据手机号查询用户信息

        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("头像");

        return ResponseResult.success(passengerUser);
    }

}
