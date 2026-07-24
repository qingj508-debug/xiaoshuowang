package com.endpoint.book.service.impl;

import com.endpoint.book.entity.BookCategory;
import com.endpoint.book.mapper.BookCategoryMapper;
import com.endpoint.book.service.IBookCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小说类别表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookCategoryServiceImpl extends ServiceImpl<BookCategoryMapper, BookCategory> implements IBookCategoryService {

}
