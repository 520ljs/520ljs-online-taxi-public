package com.ss.internalcommon.response;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 17:01
 */
@Data
public class TerminalResponse {

    private String tid;

    private Long carId;

    private Long longitude;

    private Long latitude;
}
