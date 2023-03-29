package com.ss.servicemap.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

/**
 * @Auther: ljy.s
 * @Date: 2023/3/29 - 03 - 29 - 16:51
 */
@Service
public class DirectionService {

    /**
     * 根据起点经纬度和终点经纬度获取距离（米）和时长（分钟）
     *
     * @param depLongitude  起点经度
     * @param depLatitude   起点维度
     * @param destLongitude 终点经度
     * @param destLatitude  终点维度
     * @return 返回距离和时长
     */
    public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

        // 调用第三方接口

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(123);
        directionResponse.setDuration(13);

        return ResponseResult.success(directionResponse);
    }

}
