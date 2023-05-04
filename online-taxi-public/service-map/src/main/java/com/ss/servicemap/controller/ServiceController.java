package com.ss.servicemap.controller;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 15:33
 */

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.service.ServiceFromMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 服务管理控制器
 */
@RestController
@RequestMapping("/service")
public class ServiceController {

    @Resource
    private ServiceFromMapService serviceFromMapService;

    /**
     * 创建服务
     *
     * @param name
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(String name) {

        return serviceFromMapService.add(name);
    }
}
