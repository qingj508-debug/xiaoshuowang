package com.endpoint.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.endpoint.member.mapper")
public class EndpointMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointMemberApplication.class, args);
    }

}
