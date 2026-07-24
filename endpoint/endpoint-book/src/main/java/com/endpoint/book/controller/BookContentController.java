package com.endpoint.book.controller;

import com.endpoint.book.dto.BookContentAboutRespDto;
import com.endpoint.book.entity.BookContent;
import com.endpoint.book.service.IBookContentService;
import com.endpoint.common.utils.ResultBean;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 小说内容表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/book/bookContent")
public class BookContentController {

    @Autowired
    private IBookContentService bookContentService;

        @GetMapping("content/{chapterId}")
    public ResultBean<BookContentAboutRespDto> getBookContentAbout(@PathVariable("chapterId") Long chapterId
            ,@RequestHeader (name = "Authorization",required = false) String token) {
        return bookContentService.getBookContentAbout(chapterId,token);
    }

    //根据当前id 返回上一章ID
    @GetMapping("preChapterId/{chapterId}")
    public ResultBean<Long> getPreChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookContentService.getPreChapterId(chapterId);
    }

    //根据当前id 返回下一章ID
    @GetMapping("nextChapterId/{chapterId}")
    public ResultBean<Long> getNextChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookContentService.getNextChapterId(chapterId);
    }


    @PostMapping("/saveContent")
    public ResultBean<Void> saveContent(@RequestBody BookContent bookContent){
        bookContentService.save(bookContent);
        return ResultBean.ok();
    }
}
