package com.endpoint.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.endpoint.home.mapper")
public class EndpointHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointHomeApplication.class, args);
    }

}
