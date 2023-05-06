package com.ss.application.service;

import com.ss.application.remote.ServiceOrderClient;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 11:35
 */
@Service
public class OrderService {

    @Resource
    ServiceOrderClient serviceOrderClient;

    /**
     * 创建订单
     * @param orderRequest
     * @return
     */
    public ResponseResult add(OrderRequest orderRequest){
        return serviceOrderClient.add(orderRequest);
    }


}
