package com.ss.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        // 单个服务
        config.useSingleServer().setAddress("redis://127.0.0.1:6319").setDatabase(0);

        return Redisson.create(config);

    }
}
