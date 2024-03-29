package com.ss.servicepassengeruser.service;

import com.ss.internalcommon.constant.CommonStatusEnum;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.dto.PassengerUser;
import com.ss.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ljy.s
 * @Date: 2023/3/5 - 03 - 05 - 20:38
 */
@Service
public class UserService {

    @Resource
    private PassengerUserMapper passengerUserMapper;

    public ResponseResult loginOrRegister(String passengerPhone) {
        System.out.println("user service 被调用，手机号：" + passengerPhone);
        // 根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        // 判断是用户是否存在， 存在返回手机号
        //System.out.println(passengerUsers.size() == 0 ? "无记录" : passengerUsers.get(0).getPassengerPhone());

        // 判断用户信息是否存在
        if (passengerUsers.size() == 0) {
            // 如果不存在，插入用户信息
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("bql xnh");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);

            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);

            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success();
    }

    /**
     * 根据手机号查询用户信息
     * @param passengerPhone 手机号
     * @return 返回用户信息
     */
    public ResponseResult getUserByPhone(String passengerPhone) {
        // 根据手机号查询用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        // 如果为0
        if (passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.USER_NOTE_EXISTS.getCode(), CommonStatusEnum.USER_NOTE_EXISTS.getValue());
        } else {// 如果有数据
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }

}
