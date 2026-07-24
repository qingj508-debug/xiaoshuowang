package com.endpoint.search.listener;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.search.feign.BookFeignService;
import com.endpoint.search.service.SearchService;
import com.endpoint.search.to.BookEsModel;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BookVisitAddListener {

    @Autowired
    private BookFeignService bookFeignService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SearchService searchService;

    //更新数据库 流量削峰，每本小说累积10个点击更新一次
    @RabbitListener(queues = {"UPDATE-ES-QUEUE"})
    public void updateEs(Long bookId, Channel channel, Message message) {

        log.debug("收到更新搜索引擎消息：" + bookId);
        if (redisTemplate.opsForValue().get("esIsUpdateVisit:" + bookId) == null) {
            redisTemplate.opsForValue().set("esIsUpdateVisit:" + bookId, "1", 60, TimeUnit.MINUTES);
            try {
                Thread.sleep(1000 * 5);
                ResultBean<BookEsModel> res = bookFeignService.getById(bookId);

                searchService.importToEs(res.getData());
            }catch (Exception e){
                redisTemplate.delete("esIsUpdateVisit:" + bookId);
                log.error("更新搜索引擎失败"+bookId);
            }
        }
    }
}