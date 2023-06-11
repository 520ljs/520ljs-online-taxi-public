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
}
