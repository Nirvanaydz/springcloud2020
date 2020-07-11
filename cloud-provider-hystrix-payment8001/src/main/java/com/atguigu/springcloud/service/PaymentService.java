package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "threadPool" + Thread.currentThread().getName() +
                "paymentInfo_OK is ******" + id;
    }

    // 模拟的方法出错后，有另外一个方法给其兜底，使用HystrixCommand注解
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_Timeout(Integer id){
        // 构造超时异常
        int time = 10;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "threadPool" + Thread.currentThread().getName() +
                "paymentInfo_Timeout is ******" + id + "using" + time + "seconds";

        // 构造程序运行异常
//        int age = 10/0;
//        return "threadPool" + Thread.currentThread().getName() +
//                "paymentInfo_Timeout is ******" + id + "using" + id + "seconds";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "threadPool" + Thread.currentThread().getName() +
                "***** 服务繁忙，请稍后 ******" + id;
    }
}
