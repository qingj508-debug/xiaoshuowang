package com.endpoint.search.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务
 *      1、@EnableScheduling 开启定时任务
 *      2、Scheduled 开启一个定时任务
 *      3、自动配置类 TaskSchedulingAutoConfiguration 属性绑定在TaskExecutionProperties
 */
@Slf4j
//@Component
@EnableScheduling  // 同步
@EnableAsync // 异步
public class TestScheduled {



    //@Scheduled(cron = "0/1 * * * * *")
    public void synTask() throws InterruptedException {
        sleep();
        System.out.println(Thread.currentThread().getName() + " syn-task 执行,当前时间: " + LocalDateTime.now());
    }



    @Scheduled(cron = "0/1 * * * * *")
    @Async
    public void asyncTask() throws InterruptedException {
        sleep();
        System.out.println(Thread.currentThread().getName() + " async-task 执行,当前时间: " + LocalDateTime.now());
    }

    private void sleep(){
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
