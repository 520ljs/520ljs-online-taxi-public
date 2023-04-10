package com.ss.apiboss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 10:21
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "api-boss";
    }

}
