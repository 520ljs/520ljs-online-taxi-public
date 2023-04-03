package com.ss.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: ljy.s
 * @Date: 2023/3/28 - 03 - 28 - 14:46
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

    private String profilePhoto;

}
