package com.endpoint.member.controller;

import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.dto.MemberBookshelfDto;
import com.endpoint.member.service.IMemberBookshelfService;
import com.endpoint.member.vo.MemberBookshelfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 用户书架表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/member/memberBookshelf")
public class MemberBookshelfController {


    @Autowired
    private IMemberBookshelfService memberBookshelfService;

    //查询小说是否已经加入到书架中
    @GetMapping("/isAddToBookshelf/{bookId}")
    public ResultBean isAddToBookshelf(@PathVariable("bookId") Long bookId,
                                       @RequestHeader (name="Authorization",required = false) String token ){

        if(JwtTokenUtil.isTokenExpired(token)){
            //如果没有token 或token过期 返回 isTokenExpired已经判断了为空情况
            return ResultBean.ok(false);
        }
        Long useId = JwtTokenUtil.getUserIdFromToken(token);
        ResultBean result= memberBookshelfService.isAddToBookshelf(bookId,useId);
        return result;
    }


    //加入书架
    @PostMapping("/addToBookshelf")
    public ResultBean addToBookshelf(@RequestBody MemberBookshelfVo memberBookshelfVo,
                                     @RequestHeader (name="Authorization") String token){
        Long useId = JwtTokenUtil.getUserIdFromToken(token);
        memberBookshelfVo.setMemberId(useId);
        ResultBean result= memberBookshelfService.addToBookshelf(memberBookshelfVo);
        return ResultBean.ok();
    }



    @GetMapping("/getBookShelfList")
    public ResultBean getBookShelfList(@RequestHeader (name="Authorization") String token){

        Long memberId = JwtTokenUtil.getUserIdFromToken(token);
        ResultBean<List<MemberBookshelfDto>> res =memberBookshelfService.getBookShelfList(memberId);
        return res;
    }
}
