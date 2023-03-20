package com.ss.application.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.dto.TokenResult;
import com.ss.internalcommon.util.JwtUtils;
import com.ss.internalcommon.util.RedisPrefixUtils;
import io.netty.util.internal.StringUtil;
import jdk.nashorn.internal.parser.TokenKind;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.PrintWriter;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/7 - 03 - 07 - 19:02
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true; // 返回值
        String resultString = ""; // 错误信息

        // 获取token
        String token = request.getHeader("Authorization");

        // 设置tokenResult初始值为空
        TokenResult tokenResult = null;

        // 解析token
        try {
            tokenResult = JwtUtils.parseToken(token);// 解析token
        } catch (SignatureVerificationException e) {
            resultString = "token sign error";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token time out";
            result = false;
        } catch (AlgorithmMismatchException a) {
            resultString = "token AlgorithmMismatchException";
            result = false;
        } catch (Exception e) {
            resultString = "token invalid";
            result = false;
        }

        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            // 获取phone和identity
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            // 拼接key
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity);
            // 从redis中取出token，布尔值为false就不进行以下操作
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)){// 如果为空
                resultString = "token invalid";
                result = false;
            } else {
                if (!token.trim().equals(tokenRedis.trim())){// 如果token不相等
                    resultString = "token invalid";
                    result = false;
                }
            }
        }

        // 比较我们传入的token和redis中的token是否相等

        if (!result) {// 如果result为false
            // 返回给前端
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
