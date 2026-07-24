package com.endpoint.author.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.author.vo.AuthorVo;
import com.endpoint.author.entity.Author;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.author.to.BookIndexTo;
import com.endpoint.author.to.BookTo;
import com.endpoint.author.vo.BookAddVo;
import com.endpoint.author.vo.ChapterAddVo;
import com.endpoint.common.utils.ResultBean;

import java.util.List;

/**
 * <p>
 * 作者表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IAuthorService extends IService<Author> {

    List<Author> selectList();

    ResultBean<Integer> getAuthorStatus(Long userId);

    ResultBean<Void> register(AuthorVo authorVo);

    ResultBean<Page<BookTo>> listBooks(int pageNum, int pageSize, Long userId);

    ResultBean<Void> saveBook(BookAddVo vo,String token);

    ResultBean<Void> saveBookChapter(Long bookId, ChapterAddVo vo,Long userId);

    ResultBean<Page<BookIndexTo>> listChapters(Long bookId, int pageNum, int pageSize, Long userId);
}
