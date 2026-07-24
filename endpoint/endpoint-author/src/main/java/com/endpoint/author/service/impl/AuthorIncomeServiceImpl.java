package com.endpoint.author.service.impl;

import com.endpoint.author.entity.AuthorIncome;
import com.endpoint.author.mapper.AuthorIncomeMapper;
import com.endpoint.author.service.IAuthorIncomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 稿费收入统计表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class AuthorIncomeServiceImpl extends ServiceImpl<AuthorIncomeMapper, AuthorIncome> implements IAuthorIncomeService {

}
