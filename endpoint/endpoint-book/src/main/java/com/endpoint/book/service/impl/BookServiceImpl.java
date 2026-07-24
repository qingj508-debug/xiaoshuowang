package com.endpoint.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.book.dto.BookChapterAboutRespDto;
import com.endpoint.book.dto.BookCommentRespDto;
import com.endpoint.book.dto.BookRespDto;
import com.endpoint.book.entity.Book;
import com.endpoint.book.entity.BookComment;
import com.endpoint.book.entity.BookContent;
import com.endpoint.book.entity.BookIndex;
import com.endpoint.book.feign.MemebrFeignService;
import com.endpoint.book.mapper.BookMapper;
import com.endpoint.book.service.IBookCommentService;
import com.endpoint.book.service.IBookContentService;
import com.endpoint.book.service.IBookIndexService;
import com.endpoint.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.book.to.MemberTo;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 小说表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private IBookIndexService bookIndexService;


    @Autowired
    private IBookContentService bookContentService;

    @Autowired
    private IBookCommentService bookCommentService;

    @Autowired
    private MemebrFeignService memebrFeignService;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> updateRankList() {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        //根据最后更新时间降序排序
        wrapper.orderByDesc("last_index_update_time");
        List<Book> books = this.baseMapper.selectList(wrapper);
        return  books;
    }

    @Override
    public BookRespDto getBookById(Long id) {
        BookRespDto bookRespDto = new BookRespDto();
        //1.通过id查询小说基本信息
        Book book = baseMapper.selectById(id);
        //2.查询小说第一章id
        BookIndex bookIndex = bookIndexService.getOne(new QueryWrapper<BookIndex>().
                eq("book_id", id).
                orderByAsc("index_num").
                last("limit 1"));
        //3.组装响应对象
        BeanUtils.copyProperties(book,bookRespDto);
        if (bookIndex != null) {
            bookRespDto.setFirstChapterId(bookIndex.getId());
        }
        return bookRespDto;
    }
    @Override
    public List<BookRespDto> listRecBooks(Long bookId) throws NoSuchAlgorithmException {
        //1.获取该小说分类
        Integer catId = baseMapper.selectById(bookId).getCatId();

        //2.查询该分类下的所有200个最新更新且字数大于0的小说id
        List<Book> books = baseMapper.selectList(new QueryWrapper<Book>()
                .eq("cat_id", catId)
                .gt("word_count", 0)
                .orderByDesc("last_index_update_time")
                .last("limit 200"));
        //3随机获取4个 同类小说
        List<BookRespDto> bookRespDtoList = new ArrayList<>();
        //为了避免推荐的小说重复 将选中的小说id 放入集合中
        List<Integer> recIdIndexList = new ArrayList<>();
        int count = 0;//循环从0开始
        int size = 4;
        if(books.size()<size){
            size=books.size();//如果命中小说集合长度少于4 使用集合长度为循环此次数
        }
        Random rand = SecureRandom.getInstanceStrong();


        //循环4次
        while (count < size) {
            int recIdIndex = rand.nextInt(books.size());
            if (!recIdIndexList.contains(recIdIndex)) {//随机id没有保存
                recIdIndexList.add(recIdIndex);
                Book book = books.get(recIdIndex);
                BookRespDto bookRespDto = new BookRespDto();
                BeanUtils.copyProperties(book,bookRespDto);
                bookRespDtoList.add(bookRespDto);
                count++;
            }
        }
        return bookRespDtoList;
    }

    @Override
    public BookChapterAboutRespDto getLastChapterAbout(Long bookId) {
        BookChapterAboutRespDto respDto = new BookChapterAboutRespDto();
        //1.通过id查询小说基本信息
        Book book = baseMapper.selectById(bookId);
        //2.通过最新章节id 查询 该章节信息
        BookIndex bookIndex = bookIndexService.getById(book.getLastIndexId());
        BeanUtils.copyProperties(bookIndex,respDto);

        //3.查询该小说章节总数
        long chapterTotal = bookIndexService.count(new QueryWrapper<BookIndex>().eq("book_id", bookId));
        respDto.setChapterTotal(chapterTotal);

        //4.查询章节内容 30个字
        BookContent bookContent = bookContentService.getOne(new QueryWrapper<BookContent>().eq("index_id", book.getLastIndexId()));
        respDto.setContentSummary(bookContent.getContent().substring(0,30));

        return respDto;
    }

    @Override
    public BookCommentRespDto listNewestComments(Long bookId) {
        BookCommentRespDto respDto = new BookCommentRespDto();
        //1.查询评论总数
        long commentTotal = bookCommentService.count(new QueryWrapper<BookComment>().eq("book_id", bookId));
        respDto.setCommentTotal(commentTotal);
        if(commentTotal>0){
            //2.查询最新品论列表 按时间排序 取最近5条
            List<BookComment> bookComments = bookCommentService.list(new QueryWrapper<BookComment>().eq("book_id", bookId)
                    .orderByDesc("create_time")
                    .last("limit 5"));

            //查询评论用户信息，并设置需要返回的评论用户名
            List<Long> userIds = bookComments.stream().map(BookComment::getCreateUserId).collect(Collectors.toList());
            //通过获取用户id查询所有评论的用户信息
            List<MemberTo> memberTos = memebrFeignService.queryByIds(userIds);

            //获取map集合 key是 用户id value是用户信息
            Map<Long, MemberTo> userMap = memberTos.stream().collect(Collectors.toMap(MemberTo::getId, Function.identity()));
            //通过
            List<BookCommentRespDto.CommentInfo> commentInfos = bookComments.stream().map(item -> {
                BookCommentRespDto.CommentInfo commentInfo = new BookCommentRespDto.CommentInfo();
                commentInfo.setCreateUserId(item.getCreateUserId());
                commentInfo.setId(item.getId());
                commentInfo.setCreateTime(item.getCreateTime());
                commentInfo.setCommentContent(item.getCommentContent());
                //用户名 及用户头像需要 通过map集合获取
                commentInfo.setCommentUser(userMap.get(item.getCreateUserId()).getUsername());
                commentInfo.setCommentUserPhoto(userMap.get(item.getCreateUserId()).getHeader());
                return commentInfo;
            }).collect(Collectors.toList());
            respDto.setComments(commentInfos);

        }else{
            respDto.setComments(Collections.emptyList());
        }
        return respDto;
    }

    @Override
    public void addVisitCount(Long bookId, int addCount) {
        bookMapper.addVisitCount(bookId,addCount);
     }


}
