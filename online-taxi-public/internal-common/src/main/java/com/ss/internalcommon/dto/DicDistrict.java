package com.ss.internalcommon.dto;

import lombok.Data;

/**
 * @Author:ljy.s
 * @Date:2023/4/4 - 04 - 04 - 10:25
 */
@Data
public class DicDistrict {

    private String addressCode;

    private String addressName;

    private String parentAddressCode;

    private Integer level;

}
