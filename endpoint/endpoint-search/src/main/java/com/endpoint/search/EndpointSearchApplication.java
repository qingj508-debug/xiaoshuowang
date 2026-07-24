package com.endpoint.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EndpointSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(EndpointSearchApplication.class, args);
    }

}
