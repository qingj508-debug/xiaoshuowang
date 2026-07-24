package com.endpoint.book.feign;

import com.endpoint.book.to.MemberBuyRecordTo;
import com.endpoint.book.to.MemberTo;
import com.endpoint.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author cr
 * @date 2022/11/24
 * @description
 */
@FeignClient("endpoint-member")
public interface MemebrFeignService {

    @PostMapping("/member/member/queryByIds")
     List<MemberTo> queryByIds(@RequestBody List<Long> ids);


    @GetMapping("/member/memberBuyRecord/getBuyRecord/{memberId}/{indexId}")
    public ResultBean<MemberBuyRecordTo> getBuyRecordBymemberIdAndIndexId(@PathVariable("memberId")Long memberId,
                                                                          @PathVariable("indexId")Long indexId );
}
