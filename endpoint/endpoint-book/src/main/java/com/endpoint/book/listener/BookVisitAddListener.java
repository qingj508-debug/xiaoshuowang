package com.endpoint.book.listener;

import com.endpoint.book.service.IBookService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.checkerframework.checker.units.qual.A;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookVisitAddListener {

    @Autowired
    private IBookService bookService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;




    /**
     * 更新数据库
     * 流量削峰，每本小说累积10个点击更新一次
     */
    @SneakyThrows
    @RabbitListener(queues = {"UPDATE-DB-QUEUE"})
    public void updateDb(Long bookId, Channel channel,  Message message) {

        log.debug("收到更新数据库消息：" + bookId);
        RLock lock = redissonClient.getLock("addVisitCountToDb");
        lock.lock();
        try {

            Integer visitCount = (Integer)redisTemplate.opsForValue().get("bookAddVisitCount:" + bookId);
            if (visitCount == null) {//未存储默认为0
                visitCount = 0;
            }
            redisTemplate.opsForValue().set("bookAddVisitCount:" + bookId,++visitCount);
            if (visitCount >= 100) {

                //数据库添加
                bookService.addVisitCount(bookId, visitCount);
                redisTemplate.delete("bookAddVisitCount:" + bookId);
            }
        }catch (Exception e){
            log.error("更新数据库失败"+bookId);
        }

        lock.unlock();
        Thread.sleep(1000 * 2);
    }

}