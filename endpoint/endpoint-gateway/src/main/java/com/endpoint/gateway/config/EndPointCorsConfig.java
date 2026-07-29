package com.endpoint.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cr
 * @date 2022/10/21
 * @description
 */
@Configuration
public class EndPointCorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的域,不要写*，否则cookie就无法使用了
        List<String> list = new ArrayList<>();
        list.add("http://localhost:9999");
        list.add("http://192.168.0.107:9999");
        // K8s 部署：前端 NodePort 访问源
        list.add("http://192.168.10.102:31080");
        // 网关 NodePort 直连源
        list.add("http://192.168.10.102:30888");
        config.setAllowedOrigins(list);
        //config.addAllowedOrigin("http://endpoint.com:9999");
        //config.addAllowedOrigin("http://localhost:9999");
        // 允许的头信息
        config.addAllowedHeader("*");
        // 允许的请求方式
        config.addAllowedMethod("*");
        // 是否允许携带Cookie信息
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        // 添加映射路径，拦截一切请求
        configurationSource.registerCorsConfiguration("/**",config);
        return new CorsWebFilter(configurationSource);
    }
}
