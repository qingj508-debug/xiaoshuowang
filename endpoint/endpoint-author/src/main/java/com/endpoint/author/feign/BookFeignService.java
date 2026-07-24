package com.endpoint.author.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.author.to.BookContentTo;
import com.endpoint.author.to.BookIndexTo;
import com.endpoint.author.to.BookTo;
import com.endpoint.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

/**
 * @author cr
 * @date 2022/11/29
 * @description
 */
@FeignClient("endpoint-book")
public interface BookFeignService {

    @GetMapping("/book/book/getBookByAuthorId")
    public ResultBean<Page<BookTo>> getBookByAuthorId(@RequestParam("authorId") Long authorId,
                                                      @RequestParam("pageNum") int pageNum,
                                                      @RequestParam("pageSize") int pageSize);

    @PostMapping("/book/book/saveBook")
    public ResultBean<Void> saveBook(@RequestBody BookTo book);

    @GetMapping("/book/book/checkBookName")
    public ResultBean<Long> checkBookName(@RequestParam("bookName")String bookName);


    @GetMapping("/book/book/getById")
    public ResultBean<BookTo>getById(@RequestParam("bookId")Long bookId);

    @GetMapping("/book/bookIndex/getlastChapterByBookId")
    public ResultBean<BookIndexTo> getlastChapterByBookId (@RequestParam("bookId")Long bookId);

    @PostMapping("/book/bookIndex/saveChapter")
    public ResultBean<BookIndexTo>saveChapter(@RequestBody BookIndexTo bookIndex);

    @PostMapping("/book/bookContent/saveContent")
    public ResultBean<Void> saveContent(@RequestBody BookContentTo bookContent);

    @PutMapping("/book/book/updateBook")
    public ResultBean<Void> updateBook(@RequestBody BookTo book);

    // 分页查询章节列表
    @GetMapping("/book/bookIndex/chapterListPage")
    public ResultBean<Page<BookIndexTo>> listChapters(
            @RequestParam("bookId") Long bookId,
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize);
}
