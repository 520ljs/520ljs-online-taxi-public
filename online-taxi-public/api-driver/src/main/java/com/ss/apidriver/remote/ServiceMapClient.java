package com.ss.apidriver.remote;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 10:53
 */
//@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    public ResponseResult upload(@RequestBody PointRequest pointRequest);

}
