package com.ss.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 23:59
 */
@Data
public class PassengerUser {

    private Long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

}
