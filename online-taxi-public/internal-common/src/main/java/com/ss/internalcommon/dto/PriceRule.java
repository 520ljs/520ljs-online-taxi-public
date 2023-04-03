package com.ss.internalcommon.dto;

import lombok.Data;

/**
 * @Author: ljy.s
 * @Date: 2023/3/31 - 03 - 31 - 11:08
 */
@Data
public class PriceRule {

    private String cityCode;

    private String vehicleType;

    private Double startFare;

    private Integer startMile;

    private Double unitPricePerMile;

    private Double unitPricePerMinute;

}
