package com.endpoint.author.service.impl;

import com.endpoint.author.entity.AuthorCode;
import com.endpoint.author.mapper.AuthorCodeMapper;
import com.endpoint.author.service.IAuthorCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作家邀请码表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class AuthorCodeServiceImpl extends ServiceImpl<AuthorCodeMapper, AuthorCode> implements IAuthorCodeService {

}
