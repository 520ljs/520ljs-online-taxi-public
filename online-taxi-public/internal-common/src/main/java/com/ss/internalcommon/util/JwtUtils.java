package com.ss.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ss.internalcommon.dto.TokenResult;
import com.sun.scenario.effect.Identity;

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

    // JWT_KEY 司机或乘客的手机号
    private static final String JWT_KEY_PHONE = "phone";

    // 乘客是1，司机是2
    private static final String JWT_KEY_IDENTITY = "identity";

    // 生成token
    public static String generatorToken(String passengerPhone, String identity) {
        // 整个系统中只要一个token就够了，Jwt就这一种生成方式
        Map<String, String> map = new HashMap<>();
        // 传入手机号
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);

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
        //builder.withExpiresAt(date);// 将token有效期交给服务端，所以这里不需要了

        // 生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    // 解析token
    public static TokenResult parseToken(String token) {
        // 解析token
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);

        // 封装信息
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        // 返回token信息
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }


    public static void main(String[] args) {
        String s = generatorToken("18369874587", "1");
        System.out.println("生成的token：" + s);
        System.out.println("解析-----------------");
        TokenResult tokenResult = parseToken(s);
        System.out.println("手机号：" + tokenResult.getPhone());
        System.out.println("身份：" + tokenResult.getIdentity());
    }
}
