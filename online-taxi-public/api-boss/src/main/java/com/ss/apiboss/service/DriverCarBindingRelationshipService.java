package com.ss.apiboss.service;

import com.ss.apiboss.remote.ServiceDriverUserClient;
import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/13 - 04 - 13 - 15:04
 */
@Service
public class DriverCarBindingRelationshipService {

    @Resource
    ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {

        return serviceDriverUserClient.bind(driverCarBindingRelationship);
    }

    public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {

        return serviceDriverUserClient.unbind(driverCarBindingRelationship);
    }
}
