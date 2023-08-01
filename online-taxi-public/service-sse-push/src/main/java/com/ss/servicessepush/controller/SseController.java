package com.ss.servicessepush.controller;

import com.ss.internalcommon.util.SsePrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:ljy.s
 * @Date:2023/8/1 - 08 - 01 - 15:40
 */
@RestController
@Slf4j
public class SseController {

    // 存储谁 连接
    public static Map<String, SseEmitter> sseEmitterMap = new HashMap<>();

    /**
     * 建立连接
     *
     * @param userId   用户id
     * @param identity 身份类型
     * @return
     */
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam Long userId, @RequestParam String identity) {
        log.info("用户ID：" + userId + ",身份类型：" + identity);
        SseEmitter sseEmitter = new SseEmitter(0l);

        String sseMapKey = SsePrefixUtils.generatorSseKey(userId, identity);

        sseEmitterMap.put(sseMapKey, sseEmitter);

        return sseEmitter;
    }

    /**
     * 发送消息
     *
     * @param userId   用户id
     * @param identity 身份类型
     * @param content  内容
     * @return
     */
    @GetMapping("/push")
    public String push(@RequestParam Long userId, @RequestParam String identity, @RequestParam String content) {
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId, identity);
        try {
            if (sseEmitterMap.containsKey(sseMapKey)) {
                sseEmitterMap.get(sseMapKey).send(content);
            } else {
                return "推送失败";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "给用户：" + sseMapKey + ",发送了消息：" + content;
    }

    /**
     * 关闭连接
     *
     * @param userId   用户ID
     * @param identity 身份类型
     * @return
     */
    @GetMapping("/close")
    public String close(@RequestParam Long userId, @RequestParam String identity) {
        String sseMapKey = SsePrefixUtils.generatorSseKey(userId, identity);
        System.out.println("关闭连接：" + sseMapKey);
        if (sseEmitterMap.containsKey(sseMapKey)) {
            sseEmitterMap.remove(sseMapKey);
        }
        return "close 成功";
    }

}
