package com.ss.servicedriveruser.remote;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 20:14
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/add")
    public ResponseResult<TerminalResponse> addTerminal(@RequestParam String name, @RequestParam String desc);

}
