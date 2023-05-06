package com.ss.serviceorder.controller;

import com.ss.internalcommon.dto.ResponseResult;
import com.ss.internalcommon.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:ljy.s
 * @Date:2023/5/6 - 05 - 06 - 11:28
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderInfoController {

    @PostMapping("/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest) {

        log.info("service-order"+orderRequest.getAddress());
        return null;
    }

}
