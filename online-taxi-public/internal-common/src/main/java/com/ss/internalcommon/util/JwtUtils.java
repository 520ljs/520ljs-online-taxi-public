package com.ss.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/6 - 03 - 06 - 23:14
 */
public class JwtUtils {

    // 盐
    private static final String SIGN = "520ljs@520ljs";

    // JWT_KEY
    private static final String JWT_KEY = "passengerPhone";

    // 生成token
    public static String generatorToken(String passengerPhone) {
        // 整个系统中只要一个token就够了，Jwt就这一种生成方式
        Map<String, String> map = new HashMap<>();
        // 传入手机号
        map.put(JWT_KEY, passengerPhone);

        // token过期时间 1天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        // Jwt工具生成builder，用builder去接收过期时间，盐，map去创建一个token
        JWTCreator.Builder builder = JWT.create();

        // 整合map
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        // 整合过期时间
        builder.withExpiresAt(date);

        // 生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    // 解析token
    public static String parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }


    public static void main(String[] args) {
        String s = generatorToken("18369874587");
        System.out.println("生成的token：" + s);
        System.out.println("解析token后的值：" + parseToken(s));
    }
}
