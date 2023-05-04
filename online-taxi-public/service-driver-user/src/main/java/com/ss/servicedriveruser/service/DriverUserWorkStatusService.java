package com.ss.servicedriveruser.service;

import com.ss.internalcommon.dto.DriverUserWorkStatus;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 520ljs
 * @since 2023-05-03
 */
@Service
public class DriverUserWorkStatusService {

    @Resource
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    /**
     * 更新司机状态
     * @param driverId 司机id
     * @param workStatus 状态
     * @return
     */
    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("driver_id", driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);

        driverUserWorkStatus.setWorkStatus(workStatus);

        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);

        return ResponseResult.success("");

    }
}
