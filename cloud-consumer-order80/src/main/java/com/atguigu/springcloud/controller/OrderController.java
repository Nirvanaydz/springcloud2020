package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class OrderController {
    // 单机版书写方式
//    public static final String PAYMENT_URL = "http://localhost:8001";
//    集群版写法，只写其要调用的在eureka中注册了的服务集群名称
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
    /*
    * 两个服务之间调用，restTemplate
    * */
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        log.info("插入开始...");
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        log.info("查询开始...");
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id,CommonResult.class);
    }

    @GetMapping(value = "/consumer/discovery")
    public Object discovery(){
        // 获取服务名称
        List<String> services = discoveryClient.getServices();
        for (String ele:services) {
            log.info("****element:" + ele);
        }

        // 获取具体服务集群下的服务信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-ORDER-SERVICE");
        for (ServiceInstance ele:instances) {
            log.info(ele.getServiceId()+"\t"+ele.getHost()+"\t"+ele.getPort()+"\t"+ele.getUri());
        }
        return this.discoveryClient;
    }
}
