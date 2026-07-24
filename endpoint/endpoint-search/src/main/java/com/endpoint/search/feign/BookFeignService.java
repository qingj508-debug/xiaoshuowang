package com.endpoint.search.feign;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.search.to.BookEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author cr
 * @date 2022/11/8
 * @description
 */
@FeignClient("endpoint-book")
public interface BookFeignService {
    @GetMapping("/book/book/getBookList")
    public ResultBean<List<BookEsModel>> getBookList();


    @GetMapping("/book/book/getById")
    public ResultBean<BookEsModel>getById(@RequestParam("bookId")Long bookId);


    @PostMapping("/book/book/queryBookByUpdateTimeByPage")
    public ResultBean<List<BookEsModel>> queryBookByUpdateTimeByPage(@RequestBody LocalDateTime   startDate,
                                                                     @RequestParam("limit") int limit);
}
