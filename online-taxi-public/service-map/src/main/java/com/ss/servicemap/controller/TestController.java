package com.ss.servicemap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 16:19
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service map";
    }

}
