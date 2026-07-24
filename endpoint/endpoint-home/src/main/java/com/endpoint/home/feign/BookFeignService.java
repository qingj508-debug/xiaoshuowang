package com.endpoint.home.feign;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.to.BookTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author cr
 * @date 2022/11/3
 * @description
 */
@FeignClient("endpoint-book")
public interface BookFeignService {

    @GetMapping("/book/book/updateRankList")
    public ResultBean<List<BookTo>> updateRankList();

    @PostMapping("/book/book/listByIds")
    public ResultBean<List<BookTo>>listByIds(@RequestBody List<Long> ids);

}
