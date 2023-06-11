package com.ss.internalcommon.response;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/5/24 - 05 - 24 - 11:33
 */
@Data
public class OrderDriverResponse {

    private Long driverId;

    private String driverPhone;

    private Long carId;

    /**
     * 机动车驾驶证号
     */
    private String licenseId;

    /**
     * 车辆号牌
     */
    private String vehicleNo;

    /**
     * 车辆类型
     */
    private String vehicleType;

}
