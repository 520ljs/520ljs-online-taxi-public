package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.PointRequest;
import com.ss.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 9:22
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Resource
    PointService pointService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest){

        return pointService.upload(pointRequest);
    }
}
