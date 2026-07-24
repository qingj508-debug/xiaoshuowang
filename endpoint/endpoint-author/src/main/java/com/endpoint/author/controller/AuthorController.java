package com.endpoint.author.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.author.vo.AuthorVo;
import com.endpoint.author.entity.Author;
import com.endpoint.author.service.IAuthorService;
import com.endpoint.author.to.BookIndexTo;
import com.endpoint.author.to.BookTo;
import com.endpoint.author.vo.BookAddVo;
import com.endpoint.author.vo.ChapterAddVo;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.exception.BusinessException;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 作者表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/author/author")
public class AuthorController {
    @Autowired
    private IAuthorService iAuthorService;
    @GetMapping()
    public List getAll(){
        List<Author> authors = iAuthorService.selectList();
        if(true) {
            throw new BusinessException(ResponseStatus.ERROR);
        }
        return authors;
    }

    //作者是否已注册
    @GetMapping("/status")
    public ResultBean<Integer> getAuthorStatus(@RequestHeader ("Authorization") String token ){
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        return iAuthorService.getAuthorStatus(userId);
    }
    //会员注册作家
    @PostMapping("/register")
    public ResultBean<Void> register(@Valid @RequestBody AuthorVo authorVo,@RequestHeader("Authorization") String token) {
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        authorVo.setUserId(userId);
        return iAuthorService.register(authorVo);
    }

    //查询作家发布书籍集合
    @GetMapping("/books")
    public ResultBean<Page<BookTo>> listBooks(@RequestParam(defaultValue ="1", name = "pageNum") int pageNum,
                                @RequestParam(defaultValue = "10",name = "pageSize") int pageSize,
                                @RequestHeader("Authorization") String token){
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        ResultBean<Page<BookTo>>  result = iAuthorService.listBooks(pageNum,pageSize,userId);
        return result;
    }



    //小说发布接口
    @PostMapping("book")
    public ResultBean<Void> publishBook(@Valid @RequestBody BookAddVo vo,@RequestHeader("Authorization") String token) {
        return iAuthorService.saveBook(vo,token);
    }

    //发布章节
    @PostMapping("chapter/{bookId}")
    public ResultBean<Void> saveBookChapter(@PathVariable("bookId") Long bookId, @Valid @RequestBody ChapterAddVo vo,@RequestHeader("Authorization") String token) {
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        return iAuthorService.saveBookChapter(bookId,vo,userId);
    }

    //查询章节列表
    @GetMapping("chapters/{bookId}")
    public ResultBean<Page<BookIndexTo>> listChapters(
            @PathVariable("bookId") Long bookId,
            @RequestParam(defaultValue = "1", name = "pageNum") int pageNum,
            @RequestParam(defaultValue = "10", name = "pageSize") int pageSize,
            @RequestHeader("Authorization") String token) {
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        return iAuthorService.listChapters(bookId, pageNum, pageSize, userId);
    }


    @GetMapping("getAuthor/{memberId}")
    public ResultBean<Author> getAuthorByMemberId(@PathVariable("memberId") Long memberId){
        Author author = iAuthorService.getOne(new QueryWrapper<Author>().eq("member_id", memberId));
        return ResultBean.ok(author);
    }
}
