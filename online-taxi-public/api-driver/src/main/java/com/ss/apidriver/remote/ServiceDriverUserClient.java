package com.ss.apidriver.remote;

import com.ss.internalcommon.dto.Car;
import com.ss.internalcommon.dto.DriverUser;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:ljy.s
 * @Date:2023/4/10 - 04 - 10 - 15:09
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    @RequestMapping(method = RequestMethod.GET, value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    @RequestMapping(method = RequestMethod.GET, value = "/car")
    public ResponseResult<Car> getCarById(@RequestParam Long carId);

}
