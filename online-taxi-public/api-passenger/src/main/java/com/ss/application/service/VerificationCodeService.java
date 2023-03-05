package com.ss.application.service;

import com.ss.application.remote.ServiceVerificationcodeClient;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.NumberCodeResponse;
import com.ss.internalcommon.response.TokenResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:37
 */
@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    // 乘客验证码的前缀
    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据手机号，生成key
     *
     * @param passengerPhone
     * @return
     */
    private String generatorKeyByPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 生成验证码
     *
     * @param passengerPhone 手机号
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone) {
        // 通过调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("remote number code：" + numberCode);

        // 存入redis
        System.out.println("存入redis");
        // key,value,过期时间
        String key = generatorKeyByPhone(passengerPhone);
        // 存入redis, key-value, 2分钟有效时间
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        // 返回值
        /*JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");*/
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone   手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，去redis读取验证码
        System.out.println("根据手机号，去redis读取验证码");

        // 生成key
        String key = generatorKeyByPhone(passengerPhone);

        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value：" + codeRedis);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){// 判断验证码是否为空
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){// redis中验证码和用户填写的验证码是否一样
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");

        // 颁发令牌
        System.out.println("颁发令牌");

        // 返回响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }

}
