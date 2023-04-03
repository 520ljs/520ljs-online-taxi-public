package com.ss.application.controller;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:06
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test api passenger";
    }

    /**
     * 需要有token
     *
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth test");
    }

    /**
     * 没有token也能正常返回
     *
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult noauthTest() {
        return ResponseResult.success("noauth test");
    }
}
