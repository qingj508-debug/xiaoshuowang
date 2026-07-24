package com.endpoint.home.service;

import com.endpoint.home.dto.BookCommendRespDto;
import com.endpoint.home.entity.HomeBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页小说设置表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
public interface IHomeBookService extends IService<HomeBook> {

    List<BookCommendRespDto> bookCommendList();

}
