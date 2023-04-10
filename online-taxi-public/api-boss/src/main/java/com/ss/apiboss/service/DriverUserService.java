package com.ss.apiboss.service;

import com.ss.apiboss.remote.ServiceDriverUserClient;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 11:12
 */
@Service
public class DriverUserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult addDriverUser(DriverUser driverUser) {

        return serviceDriverUserClient.addDriverUser(driverUser);
    }

}
