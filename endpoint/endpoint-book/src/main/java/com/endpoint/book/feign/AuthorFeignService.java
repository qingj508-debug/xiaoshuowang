package com.endpoint.book.feign;

import com.endpoint.book.to.AuthorTo;
import com.endpoint.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cr
 * @date 2022/12/8
 * @description
 */
@FeignClient("endpoint-author")
public interface AuthorFeignService {

    @GetMapping("/author/author/getAuthor/{memberId}")
    public ResultBean<AuthorTo> getAuthorByMemberId(@PathVariable("memberId") Long memberId);
}
