package com.ss.apidriver.service;

import com.ss.apidriver.remote.ServiceDriverUserClient;
import com.ss.apidriver.remote.ServiceVerificationCodeClient;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.constant.IdentityConstants;
import com.ss.internalcommon.constant.TokenConstants;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.VerificationCodeDTO;
import com.ss.internalcommon.response.DriverUserExistsResponse;
import com.ss.internalcommon.response.NumberCodeResponse;
import com.ss.internalcommon.response.TokenResponse;
import com.ss.internalcommon.util.JwtUtils;
import com.ss.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author:ljy.s
 * @Date:2023/4/14 - 04 - 14 - 14:44
 */
@Service
@Slf4j
public class VerificationCodeService {

    @Resource
    ServiceDriverUserClient serviceDriverUserClient;

    @Resource
    ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Resource
    StringRedisTemplate stringRedisTemplate;

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
        ResponseResult<NumberCodeResponse> numberCodeResult = serviceVerificationCodeClient.getNumberCode(6);
        NumberCodeResponse numberCodeResponse = numberCodeResult.getData();
        int numberCode = numberCodeResponse.getNumberCode();
        log.info("验证码：" + numberCode);

        // 调用第三方服务发送验证码,第三方：阿里短信服务，腾讯，华信，容联

        // 存入redis，1：key，2：存入Redis
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     *
     * @param driverPhone      手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String driverPhone, String verificationCode) {
        // 根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取验证码");

        // 生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);

        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value：" + codeRedis);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)) {// 判断验证码是否为空
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())) {// redis中验证码和用户填写的验证码是否一样
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 颁发令牌，不应该用魔法值，用常量
        String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        // 将token存到redis中,将token的有效期交给服务端控制了
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        // refreshToken要比accessToken晚过期
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        // 返回响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }

}
