package com.ss.serviceorder.remote;

import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:ljy.s
 * @Date:2023/5/9 - 05 - 09 - 1:25
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @GetMapping("/price-rule/is-new")
    public ResponseResult<Boolean> isNew(@RequestParam String fareType, @RequestParam Integer fareVersion);

}
