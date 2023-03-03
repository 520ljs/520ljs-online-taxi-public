package com.ss.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 23:53
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-verificationCode";
    }
}
