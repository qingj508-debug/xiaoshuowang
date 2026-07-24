package com.endpoint.home.service.impl;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.feign.BookFeignService;
import com.endpoint.home.service.IHomeService;
import com.endpoint.home.to.BookTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cr
 * @date 2022/11/3
 * @description
 */
@Service
public class HomeServiceImpl implements IHomeService {
    @Autowired
    private BookFeignService bookFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @SneakyThrows
    @Override
    public List<BookTo> updateRankList() {
        String json = stringRedisTemplate.opsForValue().get("updateRankJSON");
        if(StringUtils.isEmpty(json)){
            ResultBean<List<BookTo>> result = bookFeignService.updateRankList();
            if(result!=null&&result.getCode()==200){
                List<BookTo> list = result.getData();
                json = new ObjectMapper().writeValueAsString(list);
                stringRedisTemplate.opsForValue().set("updateRankJSON",json,5, TimeUnit.MINUTES);
                return  list;
            }
        }
        return new ObjectMapper().readValue(json, List.class);
    }
}