package com.endpoint.member.service.impl;

import com.endpoint.member.entity.Member;
import com.endpoint.member.mapper.MemberMapper;
import com.endpoint.member.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}
