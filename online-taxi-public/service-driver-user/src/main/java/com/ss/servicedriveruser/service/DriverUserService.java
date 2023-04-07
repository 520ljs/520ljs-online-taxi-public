package com.ss.servicedriveruser.service;

import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.DriverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author:ljy.s
 * @Date:2023/4/6 - 04 - 06 - 17:19
 */
@Service
public class DriverUserService {

    @Autowired
    private DriverUserMapper driverUserMapper;

    public ResponseResult testGetDriverUser() {

        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);

    }

    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        // 设置创建时间
        driverUser.setGmtCreate(now);
        // 设置修改时间
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);
        return ResponseResult.success("");
    }

}
