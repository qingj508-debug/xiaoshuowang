package com.endpoint.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.book.dto.BookChapterAboutRespDto;
import com.endpoint.book.dto.BookContentAboutRespDto;
import com.endpoint.book.dto.BookRespDto;
import com.endpoint.book.entity.Book;
import com.endpoint.book.entity.BookContent;
import com.endpoint.book.entity.BookIndex;
import com.endpoint.book.feign.AuthorFeignService;
import com.endpoint.book.feign.MemebrFeignService;
import com.endpoint.book.mapper.BookContentMapper;
import com.endpoint.book.service.IBookContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.book.service.IBookIndexService;
import com.endpoint.book.service.IBookService;
import com.endpoint.book.to.AuthorTo;
import com.endpoint.book.to.MemberBuyRecordTo;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.common.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 小说内容表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookContentServiceImpl extends ServiceImpl<BookContentMapper, BookContent> implements IBookContentService {

    @Autowired
    private IBookService bookService;
    @Autowired
    private IBookContentService bookContentService;
    @Autowired
    private IBookIndexService bookIndexService;
    @Autowired
    private MemebrFeignService memebrFeignService;
    @Autowired
    private AuthorFeignService authorFeignService;

    @Override
        public ResultBean<BookContentAboutRespDto> getBookContentAbout(Long chapterId,String token) {
        BookContentAboutRespDto bookContentAboutRespDto = new BookContentAboutRespDto();

        //1.先查询章节信息  可以获取bookId
        BookIndex bookIndex = bookIndexService.getById(chapterId);
        BookChapterAboutRespDto bookChapterAboutRespDto = new BookChapterAboutRespDto();
        BeanUtils.copyProperties(bookIndex,bookChapterAboutRespDto);
        bookContentAboutRespDto.setChapterInfo(bookChapterAboutRespDto);

        //2.查询章节内容
        BookContent bookContent = bookContentService.getOne(new QueryWrapper<BookContent>().eq("index_id", chapterId));
        bookContentAboutRespDto.setBookContent(bookContent.getContent());

        //3.询小说信息
        Book book = bookService.getById(bookIndex.getBookId());
        BookRespDto bookRespDto = new BookRespDto();
        BeanUtils.copyProperties(book,bookRespDto);
        bookContentAboutRespDto.setBookInfo(bookRespDto);
        //4. 判断用户是否需要进行购买
        Boolean needBuy =false;//设置默认不需要购买

        if( bookIndex.getIsVip() != null &&bookIndex.getIsVip() == 1 ){
            //4.1判断用户是否登录 ,未登录显示需要购买
           if(StringUtils.isEmpty(token) || JwtTokenUtil.isTokenExpired(token)){
               needBuy=true;
           }else{//已登录用户
               //4.2 判断是否购买过
               Long memberId = JwtTokenUtil.getUserIdFromToken(token);
               ResultBean<MemberBuyRecordTo> result = memebrFeignService.getBuyRecordBymemberIdAndIndexId(memberId, chapterId);
               if(result!=null&&result.getCode()==200){
                   MemberBuyRecordTo memberBuyRecordTo = result.getData();
                   if(memberBuyRecordTo==null){ //查询不到记录
                       needBuy =true;//需要购买
                   }
               }
               //4.3 判断是否为作者本人查看
               ResultBean<AuthorTo> authorResult = authorFeignService.getAuthorByMemberId(memberId);
               if(authorResult!=null&&authorResult.getCode()==200){
                   AuthorTo authorTo = authorResult.getData();
                   //说明该会员绑定过 作者
                   if(authorTo!=null&&book.getAuthorId().equals(authorTo.getId())){
                       //该小说作者id与 该会员的作者ID 相等  不需要购买
                       needBuy =false;
                   }

               }
            }
        }
        bookContentAboutRespDto.setNeedBuy(needBuy);
        return ResultBean.ok(bookContentAboutRespDto);
    }

    @Override
    public ResultBean<Long> getPreChapterId(Long chapterId) {
        //1.查询小说ID 和 章节号
        BookIndex bookIndex = bookIndexService.getById(chapterId);
        Long bookId = bookIndex.getBookId();
        Integer indexNum = bookIndex.getIndexNum();//获取目录号

        BookIndex one = bookIndexService.getOne(new QueryWrapper<BookIndex>().eq("book_id", bookId)
                .lt("index_num", indexNum)
                .orderByDesc("index_num")
                .last("limit 1"));


        return ResultBean.ok(Optional.ofNullable(one)
                .map(BookIndex::getId)
                .orElse(null));
    }

    @Override
    public ResultBean<Long> getNextChapterId(Long chapterId) {
        //1.查询小说ID 和 章节号
        BookIndex bookIndex = bookIndexService.getById(chapterId);
        Long bookId = bookIndex.getBookId();
        Integer indexNum = bookIndex.getIndexNum();//获取目录号

        BookIndex one = bookIndexService.getOne(new QueryWrapper<BookIndex>().eq("book_id", bookId)
                .gt("index_num", indexNum)
                .orderByAsc("index_num")
                .last("limit 1"));


        return ResultBean.ok(Optional.ofNullable(one)
                .map(BookIndex::getId)
                .orElse(null));
    }
}
