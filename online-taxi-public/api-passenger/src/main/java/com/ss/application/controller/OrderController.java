package com.ss.application.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 9:58
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    //@Resource
    //OrderService orderService;

    /**
     * 创建订单/下单
     *
     * @return
     */
    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {
        System.out.println(orderRequest);
        //return orderService.add(orderRequest);
        return null;
    }

}
