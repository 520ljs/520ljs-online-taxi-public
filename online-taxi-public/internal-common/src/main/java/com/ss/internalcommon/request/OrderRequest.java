package com.ss.internalcommon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 10:00
 */
@Data
public class OrderRequest {
    // 订单ID
    private Long orderId;

    // 乘客ID
    private Long passengerId;

    // 乘客手机号
    private String passengerPhone;

    // 下单行政区域
    private String address;

    // 出发时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departTime;

    // 下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;

    // 出发地址
    private String departure;

    // 出发经度
    private String depLongitude;

    // 出发纬度
    private String depLatitude;

    // 目的地地址
    private String destination;

    // 目的地经度
    private String destLongitude;

    // 目的地纬度
    private String destLatitude;

    // 坐标加密标识 1:gcj-02, 2:wgs84, 3:bd-09, 4:cgcs2000北斗, 0：其他
    private Integer encrypt;

    // 运价类型编码
    private String fareType;

    // 运价版本
    private Integer fareVersion;

    // 请求设备唯一码
    private String deviceCode;
}
