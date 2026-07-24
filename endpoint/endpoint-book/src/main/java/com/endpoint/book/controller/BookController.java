package com.endpoint.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.book.dto.BookChapterAboutRespDto;
import com.endpoint.book.dto.BookCommentRespDto;
import com.endpoint.book.dto.BookRespDto;
import com.endpoint.book.entity.Book;
import com.endpoint.book.service.IBookService;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 小说表 前端控制器
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Api(tags = "书籍小说模块")
@RestController
@RequestMapping("/book/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "小说更新榜查询接口")
    @GetMapping("/updateRankList")
    public ResultBean<List<Book>> updateRankList(){
        List<Book> list = bookService.updateRankList();
        return ResultBean.ok(list);
    }


    @ApiOperation(value = "通过书籍id集合查询小说")
    @PostMapping("/listByIds")
    public ResultBean<List<Book>>listByIds(@RequestBody List<Long> ids){
        List<Book> books = bookService.listByIds(ids);
        return ResultBean.ok(books);
    }



    @GetMapping("/getBookList")
    public ResultBean<List<Book>> getBookList(){
        List<Book> list = bookService.list();
        return ResultBean.ok(list);
    }

    //查询小说信息
    @GetMapping("/getBookById/{id}")
    public ResultBean<BookRespDto> getBookById(@PathVariable("id") Long id){
        BookRespDto bookRespDto=bookService.getBookById(id);
        return ResultBean.ok(bookRespDto);
    }


    //同类小说推荐
    @GetMapping("recList")
    public ResultBean<List<BookRespDto>> listRecBooks( @RequestParam(name ="bookId", required = true)Long  bookId) throws NoSuchAlgorithmException {
        List<BookRespDto> list = bookService.listRecBooks(bookId);
        return ResultBean.ok(list);
    }

    //小说最新章节相关信息
    @GetMapping("/lastChapter/about")
    public ResultBean<BookChapterAboutRespDto> getLastChapterAbout(@RequestParam(name ="bookId", required = true)Long  bookId) throws NoSuchAlgorithmException {
        BookChapterAboutRespDto  respDto = bookService.getLastChapterAbout(bookId);
        return ResultBean.ok(respDto);
    }


    //小说最新评论查询接口
    @GetMapping("/comment/newestList")
    public ResultBean<BookCommentRespDto> listNewestComments(@RequestParam(name ="bookId", required = true)Long  bookId) {

        BookCommentRespDto respDto = bookService.listNewestComments(bookId);
        return ResultBean.ok(respDto);
    }



    @GetMapping("/getBookByAuthorId")
    public ResultBean<Page<Book>> getBookByAuthorId(@RequestParam("authorId") Long authorId,
                                                           @RequestParam("pageNum") int pageNum,
                                                           @RequestParam("pageSize") int pageSize){

        Page<Book> page = new Page<>(pageNum, pageSize);
        bookService.page(page, new QueryWrapper<Book>().eq("author_id", authorId));
        return ResultBean.ok(page);
    }
    //发布保存小说
    @PostMapping("/saveBook")
    public ResultBean<Void> saveBook(@RequestBody Book book){
        bookService.save(book);
        return ResultBean.ok();
    }

    //通过小说名查询是否存在
    @GetMapping("/checkBookName")
    public ResultBean<Long> checkBookName(@RequestParam("bookName")String bookName){
        long count = bookService.count(new QueryWrapper<Book>().eq("book_name", bookName));
        return ResultBean.ok(count);
    }

    @GetMapping("/getById")
    public ResultBean<Book>getById(@RequestParam("bookId")Long bookId){
        Book book = bookService.getById(bookId);
        return ResultBean.ok(book);
    }


    @PutMapping("/updateBook")
    public ResultBean<Void> updateBook(@RequestBody Book book){
        bookService.updateById(book);
        return ResultBean.ok();
    }


    @PostMapping("/addVisitCount/{bookId}")
    public ResultBean addVisitCount(@PathVariable("bookId") Long bookId,@RequestHeader (name = "Authorization",required = false) String token) {

        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        if(userId!=null){
            //从redis中获取该  key 为 visitcount:userId:bookId 值 为bookId
            String s = redisTemplate.opsForValue().get("visitcount:"  + userId + ":" + bookId);
            if(StringUtils.isBlank(s)){
                //为空代表 该用户在6小时内 未添加该书点击量 发送消息 进行点击量添加
                rabbitTemplate.convertAndSend("ADD-BOOK-VISIT-EXCHANGE", null, bookId);
                //存储该书 并设置过期时间为6小时
                redisTemplate.opsForValue().set("visitcount:" +  userId + ":" + bookId,bookId.toString(),6, TimeUnit.HOURS);
            }
        }
        return ResultBean.ok();
    }

    @PostMapping("/queryBookByUpdateTimeByPage")
    public ResultBean<List<Book>> queryBookByUpdateTimeByPage(@RequestBody LocalDateTime startDate,
                                                              @RequestParam("limit") int limit){

        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("last_index_id")
                .gt("last_index_update_time",startDate)
                .orderByAsc("last_index_update_time").last("limit "+limit);
        List<Book> books = bookService.getBaseMapper().selectList(wrapper);
        return ResultBean.ok(books);
    }


}
