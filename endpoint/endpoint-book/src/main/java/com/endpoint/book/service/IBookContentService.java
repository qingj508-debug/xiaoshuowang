package com.endpoint.book.service;

import com.endpoint.book.dto.BookContentAboutRespDto;
import com.endpoint.book.entity.BookContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.common.utils.ResultBean;

/**
 * <p>
 * 小说内容表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IBookContentService extends IService<BookContent> {

    ResultBean<BookContentAboutRespDto> getBookContentAbout(Long chapterId,String token);

    ResultBean<Long> getPreChapterId(Long chapterId);

    ResultBean<Long> getNextChapterId(Long chapterId);
}
