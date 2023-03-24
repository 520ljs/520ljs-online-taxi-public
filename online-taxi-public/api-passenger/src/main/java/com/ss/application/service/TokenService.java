package com.ss.application.service;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/24 - 03 - 24 - 15:12
 */
@Service
public class TokenService {

    /**
     * 根据原来的refreshToken去生成新的一对token
     * @param refreshTokenSrc
     * @return
     */
    public ResponseResult refreshToken(String refreshTokenSrc) {
        // 解析refreshToken

        // 取出redis中的refreshToken

        // 校验refreshToken

        // 生成双token

        return null;
    }

}
