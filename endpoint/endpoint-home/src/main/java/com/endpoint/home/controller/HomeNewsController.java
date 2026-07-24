package com.endpoint.home.controller;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.entity.HomeNews;
import com.endpoint.home.service.IHomeNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 新闻表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
@Api(tags = "新闻资讯接口")
@RestController
@RequestMapping("/home/homeNews")
public class HomeNewsController {

    @Autowired
    private IHomeNewsService homeNewsService;

    @ApiOperation(value = "最新新闻资讯查询接口")
        @GetMapping("latestNewsList")
    public ResultBean<List<HomeNews>> latestNewsList() {
        List<HomeNews> list = homeNewsService.latestNewsList();
        return ResultBean.ok(list);
    }
}
