package com.ss.serviceverificationcode.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.NumberCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 16:41
 */
@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        System.out.println("size：" + size);
        // 生成验证码
        double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10, size - 1));
        System.out.println(mathRandom);
        int resultInt = (int) mathRandom;
        System.out.println("generator src code" + resultInt);

        // 定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt);

        return ResponseResult.success(response);
    }

    public static void main(String[] args) {
        // 获取随机数
        /*double mathRandom = (Math.random() * 9 + 1) * (Math.pow(10, 5));
        System.out.println(mathRandom);
        int resultInt = (int) mathRandom;
        System.out.println(resultInt);*/
    }
}
