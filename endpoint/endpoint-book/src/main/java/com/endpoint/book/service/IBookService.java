package com.endpoint.book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.endpoint.book.dto.BookChapterAboutRespDto;
import com.endpoint.book.dto.BookCommentRespDto;
import com.endpoint.book.dto.BookRespDto;
import com.endpoint.book.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * <p>
 * 小说表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IBookService extends IService<Book> {

    List<Book> updateRankList();

    BookRespDto getBookById(Long id);

    List<BookRespDto> listRecBooks(Long bookId) throws NoSuchAlgorithmException;

    BookChapterAboutRespDto getLastChapterAbout(Long bookId);

    BookCommentRespDto listNewestComments(Long bookId);

    void addVisitCount(Long bookId ,int addCount);
}
