package com.ss.servicemap.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.response.TrackResponse;
import com.ss.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 22:39
 */
@Service
public class TrackService {

    @Resource
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){

        return trackClient.add(tid);
    }
}
