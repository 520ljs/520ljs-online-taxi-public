package com.ss.apidriver.controller;

import com.ss.apidriver.service.PointService;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.ApiDriverPointRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 10:42
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Resource
    PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody ApiDriverPointRequest apiDriverPointRequest){

        return pointService.upload(apiDriverPointRequest);
    }
}
