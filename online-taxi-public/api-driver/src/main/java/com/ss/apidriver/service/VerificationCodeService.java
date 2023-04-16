package com.ss.apidriver.service;

import com.ss.apidriver.remote.ServiceDriverUserClient;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.DriverUserExistsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/14 - 04 - 14 - 14:44
 */
@Service
@Slf4j
public class VerificationCodeService {

    @Resource
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult checkAndsendVerificationCode(String driverPhone) {
        // 查询 service-driver-user 判断改手机号的司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
        int ifExists = data.getIfExists();
        if (ifExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITS.getCode(), CommonStatusEnum.DRIVER_NOT_EXITS.getValue(), "");
        }
        log.info(driverPhone + "   号码的司机存在");

        // 获取验证码

        // 调用第三方服务发送验证码

        // 存入redis

        return ResponseResult.success("");
    }

}
