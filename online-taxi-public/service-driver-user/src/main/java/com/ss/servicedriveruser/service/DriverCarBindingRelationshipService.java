package com.ss.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 520ljs
 * @since 2023-04-12
 */
@Service
public class DriverCarBindingRelationshipService {

    @Resource
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {

        // 判断，如果参数中的车辆和司机，已经做过绑定，则不允许再次绑定（判断的是 司机和车辆的 绑定状态）
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        // 已有绑定关系，返回错误信息
        Integer integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXITS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXITS.getValue(), "");
        }

        // 司机不能重复绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXITS.getCode(), CommonStatusEnum.DRIVER_BIND_EXITS.getValue(), "");
        }

        // 汽车不能重复绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);
        integer = driverCarBindingRelationshipMapper.selectCount(queryWrapper);
        if (integer.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXITS.getCode(), CommonStatusEnum.CAR_BIND_EXITS.getValue(), "");
        }

        // 设置当前绑定时间
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        // 设置状态
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);
        // 插入数据
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");

    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
        LocalDateTime now = LocalDateTime.now();

        // 司机 车辆 状态 的信息
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        // 查询
        List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);
        if (driverCarBindingRelationships.isEmpty()) {// 如果为空 返回关系不存在
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXITS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXITS.getValue(), "");
        }
        DriverCarBindingRelationship relationship = driverCarBindingRelationships.get(0);
        // 设置关系 解绑
        relationship.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        // 设置解绑时间
        relationship.setUnBindingTime(now);
        // 根据id更新
        driverCarBindingRelationshipMapper.updateById(relationship);

        return ResponseResult.success("");
    }

}
