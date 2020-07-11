package com.atguigu.springcloud.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
/**
 * 全局过滤器的使用，order按顺序，filter指自定义过滤
 * */
@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {

    /*
    * http://localhost:9527/payment/lb?uname=12
    * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("********coming into MyLogGatewayFilter" + new Date());
        String name = exchange.getRequest().getQueryParams().getFirst("uname");
        if (null == name){
            log.info("*****非法用户名！！！");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
