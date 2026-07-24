package com.endpoint.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.book.dto.BookIndexRespDto;
import com.endpoint.book.dto.BookRespDto;
import com.endpoint.book.entity.BookIndex;
import com.endpoint.book.service.IBookIndexService;
import com.endpoint.book.service.IBookService;
import com.endpoint.common.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 小说目录表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/book/bookIndex")
public class BookIndexController {

    @Autowired
    private IBookIndexService bookIndexService;

    @GetMapping("/chapterList")
    public ResultBean<List<BookIndexRespDto>> getChapterList(@RequestParam("bookId") Long bookId) {
        return bookIndexService.getChapterList(bookId);
    }

    // 分页查询小说章节列表
    @GetMapping("/chapterListPage")
    public ResultBean<Page<BookIndex>> getChapterListPage(
            @RequestParam("bookId") Long bookId,
            @RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "10", name = "pageSize") int pageSize) {
        Page<BookIndex> page = new Page<>(pageNum, pageSize);
        bookIndexService.page(page, new QueryWrapper<BookIndex>()
                .eq("book_id", bookId)
                .orderByAsc("index_num"));
        return ResultBean.ok(page);
    }


    //查询小说最新章节
    @GetMapping("/getlastChapterByBookId")
    public ResultBean<BookIndex> getlastChapterByBookId (@RequestParam("bookId")Long bookId){
        BookIndex bookIndex = bookIndexService.getOne(new QueryWrapper<BookIndex>()
                .eq("book_Id", bookId)
                .orderByDesc("index_num")
                .last("limit 1"));
        return ResultBean.ok(bookIndex);
    }

    @PostMapping("/saveChapter")
    public ResultBean<BookIndex>saveChapter(@RequestBody BookIndex bookIndex){
        bookIndexService.save(bookIndex);
        return ResultBean.ok(bookIndex);
    }



    //查询小说章节信息
    @GetMapping("/getBookIndexById/{id}")
    public ResultBean<BookIndex> getBookIndexById(@PathVariable("id") Long id){
        BookIndex bookIndex=bookIndexService.getById(id);
        return ResultBean.ok(bookIndex);
    }

    //查询小说第一章节
    @GetMapping("/getfirstChapterByBookId")
    public ResultBean<BookIndex> getfirstChapterByBookId (@RequestParam("bookId")Long bookId){
        BookIndex bookIndex = bookIndexService.getOne(new QueryWrapper<BookIndex>()
                .eq("book_Id", bookId)
                .orderByAsc("index_num")
                .last("limit 1"));
        return ResultBean.ok(bookIndex);
    }

}
