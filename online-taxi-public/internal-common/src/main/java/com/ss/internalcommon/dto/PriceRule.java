package com.ss.internalcommon.dto;

import lombok.Data;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/31 - 03 - 31 - 11:08
 */
@Data
public class PriceRule {

    private String cityCode;

    private String vehicleType;

    private double startFare;

    private int startMile;

    private double unitPricePerMile;

    private double unitPricePerMinute;

}
