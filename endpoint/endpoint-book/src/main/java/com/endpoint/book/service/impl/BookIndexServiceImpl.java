package com.endpoint.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.book.dto.BookIndexRespDto;
import com.endpoint.book.entity.BookIndex;
import com.endpoint.book.mapper.BookIndexMapper;
import com.endpoint.book.service.IBookIndexService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.common.utils.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 小说目录表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookIndexServiceImpl extends ServiceImpl<BookIndexMapper, BookIndex> implements IBookIndexService {


    @Override
    public ResultBean<List<BookIndexRespDto>> getChapterList(Long bookId) {
        //1.根据小说id查询该书的所有章节
        List<BookIndex> bookIndices = baseMapper.selectList(new QueryWrapper<BookIndex>()
                .eq("book_id", bookId)
                .orderByAsc("index_num"));
        //2.数据封装
        List<BookIndexRespDto> list = bookIndices.stream().map(item -> {
            BookIndexRespDto bookIndexRespDto = new BookIndexRespDto();
            BeanUtils.copyProperties(item, bookIndexRespDto);
            return bookIndexRespDto;
        }).collect(Collectors.toList());

        return ResultBean.ok(list);
    }
}
