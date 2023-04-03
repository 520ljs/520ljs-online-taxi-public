package com.ss.serviceprice.remote;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.ForecastPriceDTO;
import com.ss.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: ljy.s
 * @Date: 2023/3/30 - 03 - 30 - 17:31
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.GET,value = "/direction/driving")
    public ResponseResult<DirectionResponse> direction(@RequestBody ForecastPriceDTO forecastPriceDTO);

}
