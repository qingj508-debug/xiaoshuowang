package com.endpoint.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.exception.BusinessException;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.member.entity.Member;
import com.endpoint.member.service.IMemberService;
import com.endpoint.member.service.LoginService;
import com.endpoint.member.vo.MemberLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author cr
 * @date 2022/11/16
 * @description
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private IMemberService memberService;
    @Override
    public UserDetails Login(MemberLoginVo memberLoginVo) {
        //1.查询该手机号用户是否存在 select * from member where username=?
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("username",memberLoginVo.getUsername()));
        if (member == null) {
           throw new BusinessException(ResponseStatus.USERNAME_PASS_ERROR);
        }
        // 获取数据库的密码
        String passwordDB = member.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 和用户密码进行校验
        boolean matches = passwordEncoder.matches(memberLoginVo.getPassword(), passwordDB);

        if(matches){
            //密码验证成功 登录成功，生成JWT并返回
            Long id = member.getId();
            String token = JwtTokenUtil.createToken(id);
            UserDetails userDetails = new UserDetails();
            userDetails.setId(id);
            userDetails.setNickName(member.getNickName());
            userDetails.setToken(token);
            return userDetails;
        }else {
            //验证失败 账号或密码错误
            throw new BusinessException(ResponseStatus.USERNAME_PASS_ERROR);
        }
    }
}
