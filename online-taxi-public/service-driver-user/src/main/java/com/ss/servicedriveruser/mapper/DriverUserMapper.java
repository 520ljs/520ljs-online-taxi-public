package com.ss.servicedriveruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ss.internalcommon.dto.DriverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author:ljy.s
 * @Date:2023/4/6 - 04 - 06 - 17:14
 */
@Repository
public interface DriverUserMapper extends BaseMapper<DriverUser> {

    int select1(@Param("cityCode") String cityCode);

}
