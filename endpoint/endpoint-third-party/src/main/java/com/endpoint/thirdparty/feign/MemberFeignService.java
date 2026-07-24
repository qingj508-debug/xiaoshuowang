package com.endpoint.thirdparty.feign;

import com.endpoint.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author cr
 * @date 2022/12/7
 * @description
 */
@FeignClient("endpoint-member")
public interface MemberFeignService {

    @PostMapping("/member/member/addAmount")
    public ResultBean addAmount( @RequestParam("userId") Long userId,@RequestParam("totalAmount") Integer totalAmount);
}
