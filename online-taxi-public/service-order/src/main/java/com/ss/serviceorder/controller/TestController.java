package com.ss.serviceorder.controller;

import com.ss.internalcommon.dto.OrderInfo;
import com.ss.internalcommon.dto.ResponseResult;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import com.ss.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 11:12
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    OrderInfoMapper orderInfoMapper;

    @GetMapping("/test")
    public ResponseResult test() {

        return ResponseResult.success("bql xnh");
    }

    @Value("server-port")
    String port;

    /**
     * 测试派单逻辑
     *
     * @param orderId
     * @return
     */
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId) {

        log.info("server-port：" + port + "，并发测试：orderId：" + orderId);

        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        log.info(String.valueOf(orderInfo));
        orderInfoService.dispatchRealTimeOrder(orderInfo);
        return "test-real-time-order   success";
    }


}
