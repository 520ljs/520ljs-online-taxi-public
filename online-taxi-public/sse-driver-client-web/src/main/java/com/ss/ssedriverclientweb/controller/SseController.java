package com.ss.ssedriverclientweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ljy.s
 * @Date:2023/8/1 - 08 - 01 - 15:40
 */
@RestController
public class SseController {

    // 存储谁 连接
    public static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     * @param driverId
     * @return
     */
    @GetMapping("/connect/{driverId}")
    public SseEmitter connect(@PathVariable String driverId) {
        System.out.println("司机的ID：" + driverId);
        SseEmitter sseEmitter = new SseEmitter(0l);// 0l 永不超时

        sseEmitterMap.put(driverId, sseEmitter);

        return sseEmitter;
    }

    /**
     * 发送消息
     * @param driverId 消息接受者
     * @param content 消息内容
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam String driverId, @RequestParam String content) {
        try {
            sseEmitterMap.get(driverId).send(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "给用户：" + driverId + "，发送了消息" + content;
    }

}
