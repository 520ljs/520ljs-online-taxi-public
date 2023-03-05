package com.ss.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "service-passenger-user";
    }

}
