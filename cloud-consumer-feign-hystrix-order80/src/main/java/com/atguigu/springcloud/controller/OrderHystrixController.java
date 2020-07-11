package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "global_FallbackMethod")// 全局fallback
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    // 未加熔断机制时的客户端调服务端默认接口
    @GetMapping(value = "/consumer/payment/hystrix/timeout01/{id}")
    public String paymentInfo_Timeout01(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    // 加熔断机制时的客户端调服务端默认接口，80客户端认为1500就启动自身的服务降级机制
    // 对某一个特定的调用进行服务降级
    @GetMapping(value = "/consumer/payment/hystrix/timeout02/{id}")
    @HystrixCommand(fallbackMethod = "paymentInfo_FallbackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_Timeout02(@PathVariable("id") Integer id){
        int age = 10/0; // 程序报错也会服务降级
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    // 特定的服务降级方法
    public String paymentInfo_FallbackMethod(@PathVariable("id") Integer id){
        return "指定某一服务降级方法：我是支付模块80，对方支付系统繁忙，请10S后再试，QAQ";
    }

    // 未配置特定服务降级的接口，加上HystrixCommand注解之后默认使用全局降级方法
    @GetMapping(value = "/consumer/payment/hystrix/timeout03/{id}")
    @HystrixCommand // 未指定降级方法，默认使用全局的服务降级处理
    public String paymentInfo_Timeout03(@PathVariable("id") Integer id){
        int age = 10/0; // 程序报错也会服务降级
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }

    // 通用全局配置服务降级方法，选在注解中说明
    public String global_FallbackMethod(){
        return "全局服务降级方法，我是支付模块80，对方支付系统繁忙，请10S后再试，QAQ";
    }

}
