package com.ss.apidriver.service;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author:ljy.s
 * @Date:2023/4/14 - 04 - 14 - 14:44
 */
@Service
public class VerificationCodeService {

    public ResponseResult checkAndsendVerificationCode(String driverPhone) {
        // 查询 service-driver-user 改手机号的司机是否存在

        // 获取验证码

        // 调用第三方服务发送验证码

        // 存入redis

        return ResponseResult.success("");
    }

}
