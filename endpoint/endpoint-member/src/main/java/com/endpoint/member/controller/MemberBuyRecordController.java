package com.endpoint.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.entity.MemberBuyRecord;
import com.endpoint.member.service.IMemberBuyRecordService;
import com.endpoint.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户消费记录表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/member/memberBuyRecord")
public class MemberBuyRecordController {

    @Autowired
    private IMemberBuyRecordService memberBuyRecordService;
    //根据 会员id及章节id查询 是否购买过该章节
    @GetMapping("/getBuyRecord/{memberId}/{indexId}")
    public ResultBean<MemberBuyRecord> getBuyRecordBymemberIdAndIndexId(@PathVariable("memberId")Long memberId,
                                                                        @PathVariable("indexId")Long indexId ){
        QueryWrapper<MemberBuyRecord> queryWrapper = new QueryWrapper<MemberBuyRecord>().eq("member_id", memberId)
                                                                                        .eq("book_index_id", indexId);
        MemberBuyRecord memberBuyRecord = memberBuyRecordService.getOne(queryWrapper);
        return ResultBean.ok(memberBuyRecord);
    }

    //购买该章节
    @PostMapping("/buyBookIndex/{bookIndexId}")
    public ResultBean buyBookIndex(@PathVariable("bookIndexId")Long bookIndexId,
                                   @RequestHeader("Authorization")String token){
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        ResultBean result = memberBuyRecordService.buyBookIndex(bookIndexId,userId);
        return result;
    }


}
