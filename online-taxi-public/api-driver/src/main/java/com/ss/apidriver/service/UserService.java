package com.ss.apidriver.service;

import com.ss.apidriver.remote.ServiceDriverUserClient;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 15:08
 */
@Service
public class UserService {

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateUser(DriverUser driverUser) {

        return serviceDriverUserClient.updateUser(driverUser);
    }

}
