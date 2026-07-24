package com.endpoint.book.mapper;

import com.endpoint.book.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 小说表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface BookMapper extends BaseMapper<Book> {

    void addVisitCount(@Param("bookId")Long bookId, @Param("addCount")int addCount);
}
