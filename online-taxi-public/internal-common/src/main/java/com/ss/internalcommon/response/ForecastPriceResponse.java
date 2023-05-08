package com.ss.internalcommon.response;

import lombok.Data;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 11:48
 */
@Data
public class ForecastPriceResponse {

    private double price;

    private String cityCode;

    private String vehicleType;

}
