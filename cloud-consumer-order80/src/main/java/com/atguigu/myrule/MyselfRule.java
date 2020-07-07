package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyselfRule {

    /**
     * ribbon的
     *      负载均衡轮询算法：
     *          rest请求数 % 服务集群中的总数 分配使用集群中哪一个服务器
     *          每次重启之后rest请求数从1开始重新计数
     * */
    @Bean
    public IRule getIRule(){
        // 自定义负载均衡策略为随机，并在主启动类OrderMain80.java中配置好注解
        return new RandomRule();
    }
}
