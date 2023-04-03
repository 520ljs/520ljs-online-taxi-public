package com.ss.application.controller;

import com.ss.application.service.TokenService;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ljy.s
 * @Date: 2023/3/24 - 03 - 24 - 15:08
 */

/**
 * 接收refreshToken的请求
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {

        String refreshTokenSrc = tokenResponse.getRefreshToken();
        System.out.println("原来的 refreshToken：" + refreshTokenSrc);

        return tokenService.refreshToken(refreshTokenSrc);
    }

}
