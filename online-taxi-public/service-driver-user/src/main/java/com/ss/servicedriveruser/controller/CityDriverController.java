package com.ss.servicedriveruser.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/9 - 05 - 09 - 17:47
 */
@RestController
@RequestMapping("/city-driver")
public class CityDriverController {

    @Resource
    CityDriverUserService cityDriverUserService;

    /**
     * 根据城市码查询当前城市是否有可用司机
     *
     * @param cityCode
     * @return
     */
    @GetMapping("/is-available-driver")
    public ResponseResult isAvailableDriver(String cityCode) {

        return cityDriverUserService.isAvailableDriver(cityCode);
    }

}
