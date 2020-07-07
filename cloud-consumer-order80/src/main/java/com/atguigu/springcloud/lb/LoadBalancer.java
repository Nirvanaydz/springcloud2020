package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    // 获取服务集群信息对象
    ServiceInstance instance(List<ServiceInstance> serviceInstances);


}
