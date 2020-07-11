package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 使用编码的形式进行路由网关的映射
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_atguigu01",r -> r.path("/guonei")
        .uri("http://news.baidu.com/guonei")).build();

        return routes.build();
    }

    @Bean
    public RouteLocator customRouteLocator_guoji(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_atguigu02",r -> r.path("/guoji")
                .uri("http://news.baidu.com/guoji")).build();

        return routes.build();
    }
}
