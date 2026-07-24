package com.endpoint.home.config;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author cr
 * @date 2022/11/8
 * @description
 */

@Configuration
public class MyRedissonConfig {
    //所有对Redisson的使用都是对RedissionClient对象的操作
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        //2.使用单节点实例 注意:地址要带上redis:// 或者安全实例 rediss://
        config.useSingleServer().setAddress("redis://192.168.10.109:6379").setPassword("123456");
        //根据Config创建出RedisClient实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
