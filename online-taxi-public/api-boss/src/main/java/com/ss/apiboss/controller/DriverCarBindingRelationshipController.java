package com.ss.apiboss.controller;

import com.ss.apiboss.service.DriverCarBindingRelationshipService;
import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @Author:ljy.s
 * @Date:2023/4/13 - 04 - 13 - 14:52
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

    @Resource
    DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @RequestMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

    @RequestMapping("/unbind")
    public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

        return driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
    }

}
