package com.ss.ssedriverclientweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author:ljy.s
 * @Date:2023/8/1 - 08 - 01 - 15:40
 */
@RestController
public class SseController {

    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId) {
        System.out.println("司机的ID：" + driverId);
        SseEmitter sseEmitter = new SseEmitter(0l);// 0l 永不超时

        return sseEmitter;
    }

}
