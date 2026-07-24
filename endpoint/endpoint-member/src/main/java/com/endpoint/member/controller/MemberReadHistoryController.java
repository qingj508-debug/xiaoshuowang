package com.endpoint.member.controller;

import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.service.IMemberReadHistoryService;
import com.endpoint.member.vo.MemberBookshelfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户阅读记录表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/member/memberReadHistory")
public class MemberReadHistoryController {

    @Autowired
    private IMemberReadHistoryService memberReadHistoryService;

    @PostMapping("/addReadHistory")
    public ResultBean addReadHistory(@RequestBody MemberBookshelfVo vo
            , @RequestHeader (name="Authorization",required = false)String  token ){
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        if(userId!=null){//如果token未过期 登录过 执行添加阅读记录
            vo.setMemberId(userId);
            ResultBean res = memberReadHistoryService.addReadHistory(vo);
            return  res;
        }
        return ResultBean.ok("免费未登录用户不加记录");
    }
}
