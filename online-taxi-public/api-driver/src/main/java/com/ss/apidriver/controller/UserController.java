package com.ss.apidriver.controller;

import com.ss.apidriver.service.UserService;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 15:14
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/user")
    private ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return userService.updateUser(driverUser);
    }

}
