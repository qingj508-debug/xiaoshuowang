package com.endpoint.home.config;
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

    @Value("${REDIS_HOST}")
    private String host;

    @Value("${REDIS_PORT:6379}")
    private Integer port;

    @Value("${REDIS_PASSWORD:}")
    private String password;

    //所有对Redisson的使用都是对RedissionClient对象的操作
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        //1、创建配置
        Config config = new Config();
        //2.使用单节点实例 注意:地址要带上redis:// 或者安全实例 rediss://
        String address = String.format("redis://%s:%d", host, port);
        config.useSingleServer().setAddress(address);
        // 密码非空时才设置，兼容本地无密码的Redis环境
        if (password != null && !password.isEmpty()) {
            config.useSingleServer().setPassword(password);
        }
        //根据Config创建出RedisClient实例
        return Redisson.create(config);
    }
}
