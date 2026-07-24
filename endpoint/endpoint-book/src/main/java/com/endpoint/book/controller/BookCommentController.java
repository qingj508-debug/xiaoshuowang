package com.endpoint.book.controller;

import com.endpoint.book.entity.BookContent;
import com.endpoint.book.service.IBookCommentService;
import com.endpoint.book.vo.MemberCommentVo;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 小说评论表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/book/bookComment")
public class BookCommentController {

    @Autowired
    private IBookCommentService bookCommentService;

    @PostMapping("comment")
    public ResultBean comment(@Valid @RequestBody MemberCommentVo memberCommentVo
            , @RequestHeader ("Authorization") String token ){
        //从token 中获取用户id
        Long userid = JwtTokenUtil.getUserIdFromToken(token);
        memberCommentVo.setUserId(userid);
        return bookCommentService.saveComment(memberCommentVo);
    }
}
