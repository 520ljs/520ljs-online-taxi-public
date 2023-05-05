package com.ss.internalcommon.request;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 9:24
 */
@Data
public class PointRequest {

    private String tid;

    private String trid;

    private PointDTO[] points;

}
