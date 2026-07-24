package com.endpoint.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.exception.BusinessException;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.member.entity.Member;
import com.endpoint.member.service.IMemberService;
import com.endpoint.member.service.RegisterService;
import com.endpoint.member.vo.MemberRegistVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author cr
 * @date 2022/11/14
 * @description
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private IMemberService memberService;

    @Override
    public UserDetails regist(MemberRegistVo memberRegistVo) {
        Member member = new Member();
        //对用户名手机号进行唯一性校验
        long count = memberService.count(new QueryWrapper<Member>().eq("username", memberRegistVo.getUsername()));
        if(count>0){
            throw new BusinessException(ResponseStatus.USERNAME_EXIST);
        }
        member.setUsername(memberRegistVo.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = encoder.encode(memberRegistVo.getPassword());
        ////设置密码 进行加密存储.
        member.setPassword(password);
        member.setNickName("会员"+memberRegistVo.getUsername());

        LocalDateTime now = LocalDateTime.now();
        member.setCreateTime(now);
        member.setUpdateTime(now);
        memberService.save(member);

        //生成token并封装 UserDetails
        String token = JwtTokenUtil.createToken(member.getId());
        UserDetails userDetails = new UserDetails();
        userDetails.setId(member.getId());
        userDetails.setNickName(member.getNickName());
        userDetails.setToken(token);
        return userDetails;
    }
}
