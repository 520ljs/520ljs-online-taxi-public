package com.ss.servicedriveruser.controller;


import com.ss.internalcommon.dto.DriverCarBindingRelationship;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 520ljs
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/driver_car_binding_relationship")
public class DriverCarBindingRelationshipController {

    @Autowired
    DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    @PostMapping("/bind")
    public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

        return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
    }

}
