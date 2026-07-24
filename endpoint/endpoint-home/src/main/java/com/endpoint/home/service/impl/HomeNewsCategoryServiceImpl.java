package com.endpoint.home.service.impl;

import com.endpoint.home.entity.HomeNewsCategory;
import com.endpoint.home.mapper.HomeNewsCategoryMapper;
import com.endpoint.home.service.IHomeNewsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻类别表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
@Service
public class HomeNewsCategoryServiceImpl extends ServiceImpl<HomeNewsCategoryMapper, HomeNewsCategory> implements IHomeNewsCategoryService {

}
