package com.endpoint.author.service.impl;

import com.endpoint.author.entity.AuthorIncomeDetail;
import com.endpoint.author.mapper.AuthorIncomeDetailMapper;
import com.endpoint.author.service.IAuthorIncomeDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 稿费收入明细统计表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class AuthorIncomeDetailServiceImpl extends ServiceImpl<AuthorIncomeDetailMapper, AuthorIncomeDetail> implements IAuthorIncomeDetailService {

}
