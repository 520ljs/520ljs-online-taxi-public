package com.ss.application.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 11:42
 */
@Service
@Slf4j
public class ForecastPriceService {

    /**
     * 根据出发地和目的地 经纬度   计算预估价格
     *
     * @param depLongitude  出发地经度
     * @param depLatitude   出发地维度
     * @param destLongitude 目的地经度
     * @param destLatitude  目的地维度
     * @return 返回预估价格
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        log.info("出发地经度：" + depLongitude);
        log.info("出发地维度：" + depLatitude);
        log.info("目的地经度：" + destLongitude);
        log.info("目的地维度：" + destLatitude);

        log.info("调用计价服务，计算价格");

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(12.34);
        return ResponseResult.success(forecastPriceResponse);
    }

}
