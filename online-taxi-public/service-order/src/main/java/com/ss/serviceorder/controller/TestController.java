package com.ss.serviceorder.controller;

import com.ss.internalcommon.dto.OrderInfo;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.serviceorder.ServiceOrderApplication;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import com.ss.serviceorder.service.OrderInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 11:12
 */
@RestController
public class TestController {

    @Resource
    OrderInfoService serviceOrderClient;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @GetMapping("/test")
    public ResponseResult test(){

        return ResponseResult.success("bql xnh");
    }

    /**
     * 测试派单逻辑
     * @param orderId
     * @return
     */
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId){
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        serviceOrderClient.dispatchRealTimeOrder(orderInfo);
        return "test-real-time-order   success";
    }

}