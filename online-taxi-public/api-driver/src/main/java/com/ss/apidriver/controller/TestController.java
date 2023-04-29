package com.ss.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 14:50
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "api-driver";
    }

    /**
     * 需要授权的接口
     * @return
     */
    @GetMapping("/auth")
    public String testAuth() {
        return "auth";
    }

    /**
     * 不需要授权的接口
     * @return
     */
    @GetMapping("/noauth")
    public String testNoAuth() {
        return "no auth";
    }
}
