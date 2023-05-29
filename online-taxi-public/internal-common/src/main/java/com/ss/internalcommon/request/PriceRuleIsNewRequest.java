package com.ss.internalcommon.request;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/5/27 - 05 - 27 - 11:40
 */
@Data
public class PriceRuleIsNewRequest {

    private String fareType;

    private Integer fareVersion;

}
