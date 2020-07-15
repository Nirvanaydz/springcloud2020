package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigCenterMain3344 {

    // 开启总线配置后发送curl请求 curl -X POST "http://localhost:3344/actuator/bus-refresh"
    // 当只想通知某一个服务端时 curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class,args);
    }
}
