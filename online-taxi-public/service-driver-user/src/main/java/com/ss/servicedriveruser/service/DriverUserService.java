package com.ss.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.DriverUserWorkStatus;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.OrderDriverResponse;
import com.ss.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.ss.servicedriveruser.mapper.DriverUserMapper;
import com.ss.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Driver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ljy.s
 * @Date:2023/4/6 - 04 - 06 - 17:19
 */
@Service
public class DriverUserService {

    @Resource
    private DriverUserMapper driverUserMapper;

    @Resource
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Resource
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * test
     *
     * @return
     */
    public ResponseResult testGetDriverUser() {

        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);

    }

    /**
     * 添加司机
     *
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        // 设置创建时间
        driverUser.setGmtCreate(now);
        // 设置修改时间
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);

        // 初始化 司机工作状态表
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success("");
    }

    /**
     * 更新司机信息
     *
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        // 设置当前修改时间
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success("");
    }

    /**
     * 根据司机电话查询司机信息
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
        if (driverUsers.isEmpty()) {// 如果为空，返回司机不存在信息
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITS.getCode(), CommonStatusEnum.DRIVER_NOT_EXITS.getValue());
        }
        // 存在，取出司机信息
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    /**
     * 根据车辆Id查询订单需要的司机信息
     *
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId) {
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", carId);
        queryWrapper.eq("bind_state", DriverCarConstants.DRIVER_CAR_BIND);

        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);
        if (null == driverCarBindingRelationship) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(), CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue(), "");
        }
        Long driverId = driverCarBindingRelationship.getDriverId();

        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id", driverId);
        queryWrapper1.eq("work_status", DriverCarConstants.DRIVER_WORK_STATUS_START);

        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper1);

        if (null == driverUserWorkStatus) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(), CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue(), "");
        } else {
            QueryWrapper<DriverUser> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("id", driverId);
            DriverUser driverUser = driverUserMapper.selectOne(queryWrapper2);

            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());

            return ResponseResult.success(orderDriverResponse);
        }
    }
}
