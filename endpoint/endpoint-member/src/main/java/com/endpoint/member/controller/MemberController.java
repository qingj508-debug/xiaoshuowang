package com.endpoint.member.controller;

import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.common.valid.AddGroup;
import com.endpoint.member.entity.Member;
import com.endpoint.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/member/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;
    @PostMapping("/save")
    public ResultBean save(@Validated({AddGroup.class}) @RequestBody Member member){

        memberService.save(member);
        return ResultBean.ok();
    }


    @PostMapping("/queryByIds")
    public List<Member> queryByIds(@RequestBody List<Long> ids){
        List<Member> members = memberService.listByIds(ids);
        return members;
    }

    @GetMapping("/getAccountBalance")
    public ResultBean<Map> getAccountBalance(@RequestHeader("Authorization") String token){

        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        Member member = memberService.getById(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("accountBalance",member.getAccountBalance());
        return ResultBean.ok(map);
    }


    @PostMapping("/addAmount")
    public ResultBean addAmount(@RequestParam("userId") Long userId,@RequestParam("totalAmount") Integer totalAmount){
        Member member = memberService.getById(userId);
        member.setAccountBalance(member.getAccountBalance()+totalAmount);
        memberService.updateById(member);
        return ResultBean.ok();
    }
}
