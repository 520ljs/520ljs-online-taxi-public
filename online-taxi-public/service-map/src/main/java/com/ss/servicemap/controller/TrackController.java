package com.ss.servicemap.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 22:38
 */
@RestController
@RequestMapping("/track")
public class TrackController {

    @Resource
    TrackService trackService;

    @PostMapping("/add")
    public ResponseResult add(String tid){

        return trackService.add(tid);
    }
}
