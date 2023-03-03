package com.ss.application.controller;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:06
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test api passenger";
    }

}
