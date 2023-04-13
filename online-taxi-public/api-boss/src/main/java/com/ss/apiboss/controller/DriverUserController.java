package com.ss.apiboss.controller;

import com.ss.apiboss.service.CarService;
import com.ss.apiboss.service.DriverUserService;
import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 10:54
 */
@RestController
public class DriverUserController {

    @Resource
    private DriverUserService driverUserService;

    @Resource
    private CarService carService;

    @PostMapping("/driver-user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser) {

        return driverUserService.addDriverUser(driverUser);
    }

    @PutMapping("/driver-user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {

        return driverUserService.updateDriverUser(driverUser);
    }

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {
        return carService.addCar(car);
    }

}
