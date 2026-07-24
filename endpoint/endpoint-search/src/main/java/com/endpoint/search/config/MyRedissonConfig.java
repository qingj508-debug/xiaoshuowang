package com.endpoint.search.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cr
 * @date 2022/11/8
 * @description
 */
@Configuration
public class MyRedissonConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.password:}")
    private String password;

    // 所有对Redisson的使用都是对RedissonClient对象的操作
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        // 1、创建配置
        Config config = new Config();
        // 2、拼接单节点地址，格式固定为 redis://host:port
        String address = String.format("redis://%s:%d", host, port);
        config.useSingleServer().setAddress(address);

        // 密码非空时才设置，兼容本地无密码的Redis环境
        if (password != null && !password.isEmpty()) {
            config.useSingleServer().setPassword(password);
        }

        // 根据Config创建出RedissonClient实例
        return Redisson.create(config);
    }
}