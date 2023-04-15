package com.ss.internalcommon.response;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/4/14 - 04 - 14 - 23:48
 */
@Data
public class DriverUserExistsResponse {

    private String driverPhone;

    private int ifExists;

}
