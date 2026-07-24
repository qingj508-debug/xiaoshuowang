package com.endpoint.member.feign;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.to.BookIndexTo;
import com.endpoint.member.to.BookTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

/**
 * @author cr
 * @date 2022/12/9
 * @description
 */
@FeignClient("endpoint-book")
public interface BookFeignService {

    //查询小说信息
    @GetMapping("/book/book/getBookById/{id}")
    public ResultBean<BookTo> getBookById(@PathVariable("id") Long id);

    //查询小说章节信息
    @GetMapping("/book/bookIndex/getBookIndexById/{id}")
    public ResultBean<BookIndexTo> getBookIndexById(@PathVariable("id") Long id);


    @GetMapping("/book/bookIndex/getfirstChapterByBookId")
    public ResultBean<BookIndexTo> getfirstChapterByBookId (@RequestParam("bookId")Long bookId);


    @PostMapping("/book/book/listByIds")
    public ResultBean<List<BookTo>>listByIds(@RequestBody List<Long> ids);
}
