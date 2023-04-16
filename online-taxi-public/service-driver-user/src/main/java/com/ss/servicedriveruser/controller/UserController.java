package com.ss.servicedriveruser.controller;

import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.constant.DriverCarConstants;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.DriverUserExistsResponse;
import com.ss.servicedriveruser.service.DriverUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/4/7 - 04 - 07 - 9:37
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private DriverUserService driverUserService;

    /**
     * 新增司机
     *
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult addUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改司机
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        log.info(JSONObject.fromObject(driverUser).toString());
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 查询司机
     * 如果需要按照司机的多个条件做查询，那么此处用 /user
     *
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getUser(@PathVariable("driverPhone") String driverPhone) {

        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser driverUserDB = driverUserByPhone.getData();
        DriverUserExistsResponse response = new DriverUserExistsResponse();

        int ifExists = DriverCarConstants.DRIVER_EXISTS;
        if (driverUserDB == null) {
            ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            response.setDriverPhone(driverPhone);
            response.setIfExists(ifExists);
        } else {
            response.setDriverPhone(driverUserDB.getDriverPhone());
            response.setIfExists(ifExists);
        }


        return ResponseResult.success(response);
    }

}
