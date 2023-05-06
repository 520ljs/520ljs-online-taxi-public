package com.ss.serviceorder.controller;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 11:12
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseResult test(){

        return ResponseResult.success("bql xnh");
    }

}
