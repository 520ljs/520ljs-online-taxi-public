package com.ss.application.service;

import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.TokenConstants;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.dto.TokenResult;
import com.ss.internalcommon.response.TokenResponse;
import com.ss.internalcommon.util.JwtUtils;
import com.ss.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/24 - 03 - 24 - 15:12
 */
@Service
public class TokenService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据原来的refreshToken去生成新的一对token
     *
     * @param refreshTokenSrc
     * @return
     */
    public ResponseResult refreshToken(String refreshTokenSrc) {
        // 解析refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {// 如果为空，返回 token错误
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();

        // 取出redis中的refreshToken
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        // 校验refreshToken
        if ((StringUtils.isBlank(refreshTokenRedis)) || (!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))) {
            // 如果redis中的为空，或者redis与传入的不相等，返回 token错误
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        // 生成双token
        String refreshToken = JwtUtils.generatorToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JwtUtils.generatorToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        //stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        //stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 10, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 1, TimeUnit.MINUTES);

        // 返回响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);

        return ResponseResult.success(tokenResponse);
    }

}
