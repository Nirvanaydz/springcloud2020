package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    //服务熔断
    /*
    * 阐述：服务熔断机制，当服务熔断机制开启时（如何开启，取决于达到服务熔断机制的设定，在设定的10秒时间内，
    *       请求次数10次中，失败率达到60%，即开启服务熔断，服务熔断开启后，程序会尝试着安排一个新发送的请求进行运行，
    *       这时处于熔断器处于半开启的状态，当这一请求成功时，熔断器关闭，链路恢复，当请求失败，熔断器继续开启，
    *       链路继续断开，仍然处于开启状态，重复半开启和开启，直到恢复。）
    * */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("*****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();// 等价于 UUID.randomUUID().toString(true);

        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试,(┬＿┬)/~~     id: " +id;
    }
}
