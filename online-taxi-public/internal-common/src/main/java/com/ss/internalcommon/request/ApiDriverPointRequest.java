package com.ss.internalcommon.request;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 10:43
 */
@Data
public class ApiDriverPointRequest {

    public Long carId;

    private PointDTO[] points;

}
