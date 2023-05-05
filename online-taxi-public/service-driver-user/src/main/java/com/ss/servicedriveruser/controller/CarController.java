package com.ss.servicedriveruser.controller;


import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 520ljs
 * @since 2023-04-10
 */
@RestController
public class CarController {

    @Resource
    CarService carService;

    @PostMapping("/car")
    public ResponseResult addCar(@RequestBody Car car) {

        return carService.addCar(car);
    }

    @GetMapping("/car")
    public ResponseResult<Car> getCarById(Long carId){

        return carService.getCarById(carId);
    }

}
