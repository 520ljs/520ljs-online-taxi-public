package com.ss.application.controller;

import com.ss.application.remote.ServiceOrderClient;
import com.ss.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: ljy.s
 * @Date: 2023/3/3 - 03 - 03 - 22:06
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test api passenger";
    }

    /**
     * 需要有token
     *
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth test");
    }

    /**
     * 没有token也能正常返回
     *
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult noauthTest() {
        return ResponseResult.success("noauth test");
    }

    @Resource
    ServiceOrderClient serviceOrderClient;

    @Value("${server.port}")
    String port;

    /**
     * 测试派单逻辑
     *
     * @param orderId
     * @return
     */
    @GetMapping("/test-real-time-order/{orderId}")
    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId) {
        System.out.println("service-order port：" + port + "并发测试：api-passenger：" + orderId);
        serviceOrderClient.dispatchRealTimeOrder(orderId);
        return "test-real-time-order   success";
    }

}
