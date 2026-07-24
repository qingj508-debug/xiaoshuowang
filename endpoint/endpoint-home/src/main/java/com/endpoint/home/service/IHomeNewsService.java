package com.endpoint.home.service;

import com.endpoint.home.entity.HomeNews;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新闻表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
public interface IHomeNewsService extends IService<HomeNews> {

    List<HomeNews> latestNewsList();
}
