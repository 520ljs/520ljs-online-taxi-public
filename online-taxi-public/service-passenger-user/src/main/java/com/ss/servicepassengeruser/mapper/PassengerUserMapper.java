package com.ss.servicepassengeruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ss.internalcommon.dto.PassengerUser;
import org.springframework.stereotype.Repository;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/6 - 03 - 06 - 0:15
 */
@Repository// 注册到spring容器中
public interface PassengerUserMapper extends BaseMapper<PassengerUser> {
}
