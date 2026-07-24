package com.endpoint.book.service;

import com.endpoint.book.dto.BookIndexRespDto;
import com.endpoint.book.entity.BookIndex;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.common.utils.ResultBean;

import java.util.List;

/**
 * <p>
 * 小说目录表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IBookIndexService extends IService<BookIndex> {

    ResultBean<List<BookIndexRespDto>> getChapterList(Long bookId);
}
