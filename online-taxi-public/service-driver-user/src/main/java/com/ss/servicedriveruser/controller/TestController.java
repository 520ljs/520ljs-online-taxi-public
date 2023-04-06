package com.ss.servicedriveruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/4/6 - 04 - 06 - 16:47
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {

        return "service-driver-user";
    }

}
