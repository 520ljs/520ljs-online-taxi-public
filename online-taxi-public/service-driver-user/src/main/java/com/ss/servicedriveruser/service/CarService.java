package com.ss.servicedriveruser.service;

import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TerminalResponse;
import com.ss.servicedriveruser.mapper.CarMapper;
import com.ss.servicedriveruser.remote.ServiceMapClient;
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

    @Resource
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtModified(now);
        car.setGmtCreate(now);

        // 获得此车辆的终端id：tid
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo(), car.getId() + "");
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        // 保存车辆
        carMapper.insert(car);

        return ResponseResult.success("");
    }

}
