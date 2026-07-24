package com.endpoint.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.dto.BookCommendRespDto;
import com.endpoint.home.entity.HomeBook;
import com.endpoint.home.feign.BookFeignService;
import com.endpoint.home.mapper.HomeBookMapper;
import com.endpoint.home.service.IHomeBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.home.to.BookTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页小说设置表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
@Service
public class HomeBookServiceImpl extends ServiceImpl<HomeBookMapper, HomeBook> implements IHomeBookService {

    @Autowired
    private BookFeignService bookFeignService;
    @Autowired
    private RedissonClient redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //private  Map<String,Object> cache = new HashMap<>();
    @SneakyThrows
    @Override
    public List<BookCommendRespDto> bookCommendList() {
        //给缓存中放json字符串，拿出json字符串，还要逆转为能用的对象类型【序列化与反序列化】
        //JSON好处是跨语言，跨平台兼容。
        //1.获取 字符串类型 对象 并通过key BookCommendJSON ,获取redis中保存数据
        String json = stringRedisTemplate.opsForValue().get("BookCommendJSON");
        //2.判断是否wei空  为空 redis中没有数据 ,需要从数据库查询

        if(StringUtils.isEmpty(json)) {
            System.out.println("缓存不命中,查询数据库.....");
            List<BookCommendRespDto> list = getBookCommendListByDb();

            return list;
        }
        System.out.println("缓存命中...直接返回..");
        return new ObjectMapper().readValue(json, List.class);
    }
    @SneakyThrows
    @Nullable
    private List<BookCommendRespDto> getBookCommendListByDb() {
        RLock lock = redisson.getLock("BookCommendJSON-lock");
        lock.lock();//加锁
        try {
            String json = stringRedisTemplate.opsForValue().get("BookCommendJSON");
            if (!StringUtils.isEmpty(json)) {
                return new ObjectMapper().readValue(json, List.class);
            }
            System.out.println("重新查询了数据库");
            //1.从book_setting查询所有推荐小说
            List<HomeBook> homeBooks = this.baseMapper.selectList(new QueryWrapper<HomeBook>().orderByAsc("sort"));

            if (homeBooks != null && homeBooks.size() > 0) {
                //2. 获取推荐小说id 封装到list集合
                List<Long> bookIds = homeBooks.stream().map(item -> {
                    return item.getBookId();
                }).collect(Collectors.toList());
                //简写 bookSettings.stream().map(BookSetting::getBookId).collect(Collectors.toList());

                //3.据小说ID列表查询相关的小说信息列表
                ResultBean<List<BookTo>> result = bookFeignService.listByIds(bookIds);

                if (result != null && result.getCode() == 200) {
                    List<BookTo> books = result.getData();
                    // 组装 HomeBookRespDto 列表数据并返回
                    if (!CollectionUtils.isEmpty(books)) {
                        //获取map集合  key为 书籍id 值为 Book对象
                        Map<Long, BookTo> bookMap = books.stream()
                                .collect(Collectors.toMap(BookTo::getId, Function.identity()));
                        //通过书籍ID 获取推荐书籍其他属性
                        List<BookCommendRespDto> collect = homeBooks.stream().map(v -> {
                            BookTo book = bookMap.get(v.getBookId());
                            BookCommendRespDto bookRespDto = new BookCommendRespDto();
                            BeanUtils.copyProperties(book, bookRespDto);//复制属性 要优先复制属性再单独设值
                            //设置 类型及书籍id
                            bookRespDto.setType(v.getType());
                            bookRespDto.setBookId(v.getBookId());
                            return bookRespDto;
                        }).collect(Collectors.toList());

                        json = new ObjectMapper().writeValueAsString(collect);
                        stringRedisTemplate.opsForValue().set("BookCommendJSON", json);
                        return collect;
                    }
                }
            }
            return Collections.emptyList();
        }finally {
            lock.unlock();
        }
    }
}
