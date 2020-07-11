package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;
/**
 * 为订单服务中调用了PaymentHystrixService接口的类提供服务降级处理方法，
 * 主要是在添加了FeignClient注解的类中增加fallback注解值，并加上实现了
 * PaymentHystrixService接口的服务降级处理方法类PaymentFallbackService
 * 这样在controller层也不需要对调用了接口的方法作特殊的处理，就可以实现服务降级处理
 * */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "------ PaymentFallbackService, ok fall back";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "------ PaymentFallbackService, out fall back";
    }
}
