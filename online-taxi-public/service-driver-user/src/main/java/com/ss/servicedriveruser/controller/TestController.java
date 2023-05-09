package com.ss.servicedriveruser.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.DriverUserMapper;
import com.ss.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/6 - 04 - 06 - 16:47
 */
@RestController
public class TestController {

    @Resource
    private DriverUserService driverUserService;

    // 测试mapper中的xml是否正常使用
    @Resource
    DriverUserMapper driverUserMapper;

    @GetMapping("/test")
    public String test() {

        return "service-driver-user";
    }

    @GetMapping("/testDB")
    public ResponseResult testDB() {

        return driverUserService.testGetDriverUser();
    }

    @GetMapping("/test-xml")
    public int testXml(String arg){
        int i = driverUserMapper.select1("3");
        return i;
    }

}
