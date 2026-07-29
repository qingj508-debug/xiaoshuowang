package com.endpoint.search.scheduled;

import com.endpoint.search.feign.BookFeignService;
import com.endpoint.search.service.SearchService;
import com.endpoint.search.to.BookEsModel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 小说导入搜索引擎
 *
 * @author Administrator
 */
@Slf4j
@Component
@EnableScheduling  // 同步
public class BookToEsScheduled {

    @Autowired
    private BookFeignService bookFeignService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SearchService searchService;

    /**
     * 1分钟导入一次
     */
    @SneakyThrows
    @Scheduled(fixedRate = 1000 * 60)
    public void saveToEs() {
        System.out.println(LocalDateTime.now());
        //TODO 引入Redisson框架实现分布式锁
        //可以重复更新，只是效率可能略有降低，所以暂不实现分布式锁
        RLock lock = redissonClient.getLock("esTransLock");
        lock.lock();
        try {
            //查询需要更新的小说
            LocalDateTime lastDate = (LocalDateTime) redisTemplate.opsForValue().get("esLastUpdateTime");
            if (lastDate == null) {
                //设置查询的更新时间
                lastDate = LocalDateTime.parse("2022-01-01T00:00:00.000");
            }
            //获取更新的小说
            List<BookEsModel> books = bookFeignService.queryBookByUpdateTimeByPage(lastDate, 100).getData();
            for (BookEsModel bookEsModel : books) {
                searchService.importToEs(bookEsModel);
                String lastIndexUpdateTime = bookEsModel.getLastIndexUpdateTime();//记录最后更新时间
                System.out.println("------->>>>"+lastIndexUpdateTime);
                lastDate = LocalDateTime.parse(lastIndexUpdateTime);
            }
            //更新一轮后 记录在redis中
            redisTemplate.opsForValue().set("esLastUpdateTime",lastDate);

        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        lock.unlock();
     }

}
