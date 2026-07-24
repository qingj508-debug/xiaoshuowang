package com.endpoint.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.home.entity.HomeNews;
import com.endpoint.home.mapper.HomeNewsMapper;
import com.endpoint.home.service.IHomeNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 新闻表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
@Service
public class HomeNewsServiceImpl extends ServiceImpl<HomeNewsMapper, HomeNews> implements IHomeNewsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @SneakyThrows
    @Override
    public List<HomeNews> latestNewsList() {
        String json = stringRedisTemplate.opsForValue().get("latestNewsJSON");
        if(StringUtils.isEmpty(json)){
            List<HomeNews> list = this.baseMapper.selectList(new QueryWrapper<HomeNews>().last("limit 2"));
            json = new ObjectMapper().writeValueAsString(list);
            stringRedisTemplate.opsForValue().set("latestNewsJSON",json);
            return list;
        }
        return new ObjectMapper().readValue(json, List.class);
    }
}
