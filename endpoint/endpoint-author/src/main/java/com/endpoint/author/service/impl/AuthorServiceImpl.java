package com.endpoint.author.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.author.to.BookContentTo;
import com.endpoint.author.to.BookIndexTo;
import com.endpoint.author.vo.AuthorVo;
import com.endpoint.author.entity.Author;
import com.endpoint.author.feign.BookFeignService;
import com.endpoint.author.mapper.AuthorMapper;
import com.endpoint.author.service.IAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.author.to.BookTo;
import com.endpoint.author.vo.BookAddVo;
import com.endpoint.author.vo.ChapterAddVo;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.common.utils.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 作者表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements IAuthorService {

    @Value("${book.price.word-count}")
    private BigDecimal wordCount;
    @Value("${book.price.endpint-coin}")
    private BigDecimal endpintCoin;

    @Autowired
    private BookFeignService bookFeignService;


    @Override
    public List<Author> selectList() {
        return this.list();
    }

    @Override
    public ResultBean<Integer> getAuthorStatus(Long userId) {
        //1.通过userId查询 该用户是否已经绑定 作家
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", userId));
        return Objects.isNull(author) ? ResultBean.ok(null) : ResultBean.ok(author.getStatus());
    }

    @Override
    public ResultBean<Void> register(AuthorVo authorVo) {
        //1.查询该用户是否已经是作家
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", authorVo.getUserId()));
        if (author != null) {
            //该会员已经申请过作家
            return ResultBean.fail(ResponseStatus.AUTHOR_EXISTS);
        }
        //2.判断笔名是否有重复   手机号 邮箱 QQ 都可以重复
        Long pen_name = baseMapper.selectCount(new QueryWrapper<Author>().eq("pen_name", authorVo.getPenName()));
        if(pen_name.intValue()>0){
            //该笔名已经被注册
            return ResultBean.fail(ResponseStatus.AUTHOR_PEN_NAME_EXISTS);
        }
        Author author1 = new Author();
        BeanUtils.copyProperties(authorVo,author1);
        author1.setMemberId(authorVo.getUserId());
        author1.setCreateTime(LocalDateTime.now());
        author1.setInviteCode("0");//邀请码默认0
        author1.setStatus(0);//0正常状态

        baseMapper.insert(author1);
        return ResultBean.ok();
    }

    @Override
    public ResultBean<Page<BookTo>> listBooks(int pageNum, int pageSize, Long userId) {
        //1.通过 用户id查询出 作家id
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", userId));

        if(author ==null){
            return ResultBean.ok(null);
        }
        //2.远程调用方法查询作家小说列表
        ResultBean<Page<BookTo>> bookpage = bookFeignService.getBookByAuthorId(author.getId(), pageNum, pageSize);

        return bookpage;
    }

    @Override
    public ResultBean<Void> saveBook(BookAddVo vo,String token) {
        //1.校验小说名是否存在
        ResultBean<Long> result= bookFeignService.checkBookName(vo.getBookName());

        if(result.getData()>0){
           return ResultBean.fail(ResponseStatus.AUTHOR_BOOK_NAME_EXIST);
        }
        BookTo bookTo = new BookTo();
        //2.设置作家信息
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", userId));
        bookTo.setAuthorId(author.getId());
        bookTo.setAuthorName(author.getPenName());

        //3.设置小说其他信息 注意在这里 BookAddVo 和BookTo中属性名有些不一样
        bookTo.setBookName(vo.getBookName());
        bookTo.setCatId(vo.getCategoryId());
        bookTo.setCatName(vo.getCategoryName());
        bookTo.setPicUrl(vo.getPicUrl());
        bookTo.setBookDesc(vo.getBookDesc());
        bookTo.setWorkDirection(vo.getWorkDirection());
        bookTo.setIsVip(0);
        bookTo.setCreateTime(LocalDateTime.now());
        bookTo.setUpdateTime(LocalDateTime.now());
        bookTo.setScore(0f);    //分数默认0
        bookTo.setWordCount(0);//字数默认0
        // 保存小说信息
        bookFeignService.saveBook(bookTo);
        return ResultBean.ok();
    }

    @Transactional
    @Override
    public ResultBean<Void> saveBookChapter(Long bookId, ChapterAddVo vo,Long userId) {
        //1.校验该小说是否属于当前作家
        //1.1 获取作家id
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", userId));
        Long id = author.getId();
        //1.2获取小说 中作家id
        BookTo bookTo = bookFeignService.getById(bookId).getData();
        Long authorId = bookTo.getAuthorId();
        if(!Objects.equals(id,authorId)){
            return ResultBean.fail(ResponseStatus.USER_UN_AUTH);
        }
        //2.保存章节信息到小说章节表
        //2.1 查询小说最新章节
        int indexNum =0;//最新章节号默认为0
        BookIndexTo bookIndexTo = bookFeignService.getlastChapterByBookId(bookId).getData();
        if(bookIndexTo!=null){
            indexNum = bookIndexTo.getIndexNum()+1;
        }
        //计算章节有效字数
        int validWordCount = StringUtil.getStrValidWordCount(vo.getChapterContent());
        //计算价格  总字数 乘以 5 除以1000 =  该章节终点币价格 divide(除数,返回的小数位数,舍入模式  截断小数    )
        int bookPrice = new BigDecimal(validWordCount).multiply(endpintCoin).divide(wordCount,0, RoundingMode.DOWN).intValue();

        //2.2将章节信息插入到bookindex
        BookIndexTo newBookIndex = new BookIndexTo();
        newBookIndex.setBookId(bookId);
        newBookIndex.setIndexNum(indexNum);
        newBookIndex.setIsVip(vo.getIsVip());
        newBookIndex.setIndexName(vo.getChapterName());
        newBookIndex.setWordCount(validWordCount);
        newBookIndex.setCreateTime(LocalDateTime.now());
        newBookIndex.setUpdateTime(LocalDateTime.now());
        newBookIndex.setBookPrice(bookPrice);
        Long bookIndexId = bookFeignService.saveChapter(newBookIndex).getData().getId();

        //2.3 将章节内容保存到 book_content表中
        BookContentTo bookContentTo = new BookContentTo();
        bookContentTo.setContent(vo.getChapterContent());
        bookContentTo.setIndexId(bookIndexId);
        bookFeignService.saveContent(bookContentTo);

        //3.更新小说表endpoint.book  最新章节信息和小说总字数信息
        BookTo newBookTo = new BookTo();
        newBookTo.setId(bookId);
        newBookTo.setLastIndexId(bookIndexId);
        newBookTo.setLastIndexName(newBookIndex.getIndexName());
        newBookTo.setLastIndexUpdateTime(LocalDateTime.now());
        newBookTo.setWordCount(bookTo.getWordCount()+validWordCount);
        newBookTo.setUpdateTime(LocalDateTime.now());
        bookFeignService.updateBook(newBookTo);

        //TODO 涉及到远程调用保存 可能会出现分布式事务 问题
        return ResultBean.ok();
    }

    @Override
    public ResultBean<Page<BookIndexTo>> listChapters(Long bookId, int pageNum, int pageSize, Long userId) {
        // 1. 获取作家id
        Author author = baseMapper.selectOne(new QueryWrapper<Author>().eq("member_id", userId));
        if (author == null) {
            return ResultBean.ok(null);
        }
        // 2. 校验该小说是否属于当前作家
        BookTo bookTo = bookFeignService.getById(bookId).getData();
        if (bookTo == null || !Objects.equals(author.getId(), bookTo.getAuthorId())) {
            return ResultBean.fail(ResponseStatus.USER_UN_AUTH, null);
        }
        // 3. 远程调用分页查询章节列表
        return bookFeignService.listChapters(bookId, pageNum, pageSize);
    }
}
