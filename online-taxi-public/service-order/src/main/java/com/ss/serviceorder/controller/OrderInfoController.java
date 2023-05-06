package com.ss.serviceorder.controller;


import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import com.ss.serviceorder.mapper.OrderInfoMapper;
import com.ss.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 520ljs
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @Resource
    OrderInfoService orderInfoService;

    @GetMapping("/testMapper")
    public String testMapper() {
        return orderInfoService.testMapper();
    }

    /**
     * 创建订单
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {

        log.info("service-order" + orderRequest.getAddress());

        return orderInfoService.add(orderRequest);
    }

}
