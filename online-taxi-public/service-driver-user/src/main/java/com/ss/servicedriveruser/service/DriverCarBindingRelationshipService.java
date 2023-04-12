package com.ss.servicedriveruser.service;

import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 520ljs
 * @since 2023-04-12
 */
@Service
public class DriverCarBindingRelationshipService {

    @Autowired
    DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
        // 设置当前绑定时间
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        // 设置状态
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);
        // 插入数据
        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("");

    }

}
