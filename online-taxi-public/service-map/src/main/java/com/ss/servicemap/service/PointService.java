package com.ss.servicemap.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.PointRequest;
import com.ss.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/5 - 05 - 05 - 9:23
 */
@Service
public class PointService {

    @Resource
    PointClient pointClient;

    /**
     * 轨迹点上传
     * @param pointRequest
     * @return
     */
    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }
}
