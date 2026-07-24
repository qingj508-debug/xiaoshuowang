package com.endpoint.home.controller;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.dto.BookCommendRespDto;
import com.endpoint.home.service.IHomeBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页小说设置表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-11-04
 */
@Api(tags = "首页推荐")
@RestController
@RequestMapping("/home/homeBook")
public class HomeBookController {
    @Autowired
    private IHomeBookService homeBookService;

    @ApiOperation(value = "首页小说推荐列表查询接口")
    @GetMapping("/bookCommendList")
    public ResultBean<List<BookCommendRespDto>> bookCommendList(){
        List<BookCommendRespDto> list =  homeBookService.bookCommendList();
        return  ResultBean.ok(list);
    }
}
