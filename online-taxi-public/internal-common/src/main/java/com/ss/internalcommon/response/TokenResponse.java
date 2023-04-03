package com.ss.internalcommon.response;

import lombok.Data;

/**
 * @Author: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 23:41
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

}
