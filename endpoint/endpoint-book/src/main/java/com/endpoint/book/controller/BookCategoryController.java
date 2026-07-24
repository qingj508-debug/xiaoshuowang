package com.endpoint.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.book.entity.BookCategory;
import com.endpoint.book.service.IBookCategoryService;
import com.endpoint.common.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 小说类别表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Api(tags = "小说分类")
@RestController
@RequestMapping("/book/bookCategory")
public class BookCategoryController {

    @Autowired
    private IBookCategoryService bookCategoryService;

    @ApiOperation(value = "小说分类列表查询接口")
    @GetMapping("listByWorkDirection")
    public ResultBean<List<BookCategory>> listByWorkDirection(
           @RequestParam(name ="workDirection", required = true) Integer workDirection) {
        List<BookCategory> list = bookCategoryService.list(new QueryWrapper<BookCategory>().eq("work_direction", workDirection));
        return ResultBean.ok(list);
    }
}
