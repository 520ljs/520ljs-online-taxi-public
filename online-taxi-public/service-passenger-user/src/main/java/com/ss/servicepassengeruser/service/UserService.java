package com.ss.servicepassengeruser.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicepassengeruser.dto.PassengerUser;
import com.ss.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:38
 */
@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service 被调用，手机号：" + passengerPhone);
        // 根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers == null ? "无记录" : passengerUsers.get(0).getPassengerPhone());

        // 判断用户信息是否存在

        // 如果不存在，输入用户信息

        return ResponseResult.success();
    }

}
