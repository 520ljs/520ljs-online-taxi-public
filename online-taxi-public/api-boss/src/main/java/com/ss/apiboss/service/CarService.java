package com.ss.apiboss.service;

import com.ss.apiboss.remote.ServiceDriverUserClient;
import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/13 - 04 - 13 - 13:38
 */
@Service
public class CarService {

    @Resource
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addCar(Car car) {
        return serviceDriverUserClient.addCar(car);
    }

}
