package com.ss.servicepassengeruser.service;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:38
 */
@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service 被调用，手机号：" + passengerPhone);
        // 根据手机号查询用户信息

        // 判断用户信息是否存在

        // 如果不存在，输入用户信息

        return ResponseResult.success();
    }

}
