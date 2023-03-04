package com.ss.application.remote;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/4 - 03 - 04 - 21:13
 */
@FeignClient("service-verificationcode")
public interface ServiceVerificationcodeClient {

    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/6")
    ResponseResult<NumberCodeResponse> getNumberCode();

}
