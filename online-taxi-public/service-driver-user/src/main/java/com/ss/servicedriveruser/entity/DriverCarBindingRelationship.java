package com.ss.servicedriveruser.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 520ljs
 * @since 2023-04-12
 */
@Data
@TableName("driver_car_binding_relationship")
public class DriverCarBindingRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 车辆ID
     */
    private Long carId;

    /**
     * 绑定状态：1：绑定，2：解绑
     */
    private Integer bindState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;

}
