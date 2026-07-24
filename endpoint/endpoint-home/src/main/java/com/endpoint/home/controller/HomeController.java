package com.endpoint.home.controller;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.service.IHomeService;
import com.endpoint.home.to.BookTo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author cr
 * @date 2022/11/3
 * @description
 */
@Api(tags = "首页相关接口")
@RestController
@RequestMapping("/home")
public class HomeController {




    @Autowired
    private RedissonClient redisson;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IHomeService homeService;
    @ApiOperation(value = "小说更新榜查询接口")
    @GetMapping("/updateRankList")
    public ResultBean<List<BookTo>> updateRankList(){
        List<BookTo> list =  homeService.updateRankList();
        return ResultBean.ok(list);
    }


    @GetMapping("/testLock")
    @ResponseBody
    public String testLock() {
        //1、获取同一把锁，只要锁的名字一样，就是同一把锁，
        RLock lock = redisson.getLock("my-lock");
        //2.加锁
        lock.lock(10, TimeUnit.SECONDS);//阻塞式等待
        try{
            System.out.println("加锁成功,执行业务:"+Thread.currentThread().getId());
            Thread.sleep(30000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("解锁成功:"+Thread.currentThread().getId());
            lock.unlock();
        }
        return "testLock";
    }



    @GetMapping("/write")
    @ResponseBody
    public String writeValue() {
        String str="";
        //获取读写锁对象  读写都是 同一个 key my-rwlock
        RReadWriteLock lock = redisson.getReadWriteLock("my-rwlock");
        RLock rLock = lock.writeLock();
        rLock.lock();
        try {
            System.out.println("写锁加锁成功。。。。"+Thread.currentThread().getId());
            //模拟获取UUID 写入redis
            str = UUID.randomUUID().toString();
            Thread.sleep(30000);
            redisTemplate.opsForValue().set("rwtest", str);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();//解锁
        }
        return str;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue() {
        String str="";
        //获取读写锁对象  读写都是 同一个 key my-rwlock
        RReadWriteLock lock = redisson.getReadWriteLock("my-rwlock");
        //获取读锁
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            System.out.println("读锁加锁成功。。。。"+Thread.currentThread().getId());
            str = redisTemplate.opsForValue().get("rwtest");
            Thread.sleep(30000);//业务执行30秒
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //解锁
            rLock.unlock();
        }
        return str;
    }


    //占坑
    @GetMapping("/wc")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore wc = redisson.getSemaphore("wc");
        //占一个坑位
        wc.acquire();//获取一个信号,获取一个值 该方法为阻塞方法,获取成功才返回ok
        return "ok";
    }

    @GetMapping("/leave")
    @ResponseBody
    public String go() throws InterruptedException {
        RSemaphore wc = redisson.getSemaphore("wc");
        //释放一个坑位
        wc.release();
        return "ok";
    }
    /**
     * 放假锁门,有5个部门,所有部门都走之后,才锁门
     */
    @GetMapping("/lockdoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);//等待五个部门
        door.await();//等待闭锁完成
        return "放假了锁门......";
    }

    /**
     * @param id 传入部门
     */
    @GetMapping("/goOffWork/{id}")
    @ResponseBody
    public String goOffWork(@PathVariable("id") Long id){
        //部门走人
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();//每执行一次方法  计数减1
        return id+"部门的人都走了.....";
    }

}
