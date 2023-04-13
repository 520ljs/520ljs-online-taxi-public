package com.ss.apiboss.service;

import com.ss.apiboss.remote.ServiceDriverUserClient;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Driver;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 11:12
 */
@Service
public class DriverUserService {

    @Resource
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 添加司机信息
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {

        return serviceDriverUserClient.addDriverUser(driverUser);
    }

    /**
     * 修改司机信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateDriverUser(driverUser);
    }

}
