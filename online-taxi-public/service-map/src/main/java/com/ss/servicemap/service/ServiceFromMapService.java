package com.ss.servicemap.service;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.servicemap.remote.ServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/4 - 05 - 04 - 15:34
 */
@Service
public class ServiceFromMapService {

    @Resource
    private ServiceClient serviceClient;

    /**
     * 创建服务
     * @param name
     * @return
     */
    public ResponseResult add(String name){

        return serviceClient.add(name);

    }
}
