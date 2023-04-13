package com.ss.servicedriveruser.service;

import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 21:26
 */
@Service
public class CarService {

    @Resource
    private CarMapper carMapper;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtModified(now);
        car.setGmtCreate(now);

        carMapper.insert(car);
        return ResponseResult.success("");
    }

}
