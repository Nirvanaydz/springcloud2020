package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced //开启负载均衡，才可以让客户知道调用什么服务集群
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    // 类似于applicationContext.xml <bean id="..." class="...">
}
