package com.endpoint.book.service.impl;

import com.endpoint.book.entity.BookAuthor;
import com.endpoint.book.mapper.BookAuthorMapper;
import com.endpoint.book.service.IBookAuthorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作者表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookAuthorServiceImpl extends ServiceImpl<BookAuthorMapper, BookAuthor> implements IBookAuthorService {

}
