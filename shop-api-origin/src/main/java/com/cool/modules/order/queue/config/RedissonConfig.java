package com.cool.modules.order.queue.config;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (StringUtils.isNotBlank(redisPassword)) {
            config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort).setPassword(redisPassword);
        } else {
            config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        }
        return Redisson.create(config);
    }
}
