package com.ss.servicedriveruser.service;

import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TerminalResponse;
import com.ss.internalcommon.response.TrackResponse;
import com.ss.servicedriveruser.mapper.CarMapper;
import com.ss.servicedriveruser.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 添加车辆信息
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtModified(now);
        car.setGmtCreate(now);

        // 获得此车辆的终端id：tid
        ResponseResult<TerminalResponse> responseResult = serviceMapClient.addTerminal(car.getVehicleNo(), car.getId() + "");
        String tid = responseResult.getData().getTid();
        car.setTid(tid);

        // 获得此车辆的轨迹id：trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();

        car.setTrid(trid);
        car.setTrname(trname);

        // 保存车辆
        carMapper.insert(car);

        return ResponseResult.success("");
    }

    /**
     * 根据车辆id查询车辆信息
     * @param id
     * @return
     */
    public ResponseResult<Car> getCarById(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<Car> cars = carMapper.selectByMap(map);

        return ResponseResult.success(cars.get(0));
    }

}
