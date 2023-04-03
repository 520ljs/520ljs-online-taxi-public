package com.ss.internalcommon.request;

import lombok.Data;

/**
 * @Author: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 11:21
 */
@Data
public class ForecastPriceDTO {

    private String depLongitude;

    private String depLatitude;

    private String destLongitude;

    private String destLatitude;

}
