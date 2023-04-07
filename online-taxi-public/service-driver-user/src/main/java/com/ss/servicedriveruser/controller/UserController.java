package com.ss.servicedriveruser.controller;

import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/4/7 - 04 - 07 - 9:37
 */
@RestController
public class UserController {

    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        return driverUserService.addDriverUser(driverUser);
    }


}
