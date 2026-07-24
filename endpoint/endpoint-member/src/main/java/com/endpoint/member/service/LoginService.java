package com.endpoint.member.service;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.member.vo.MemberLoginVo;

/**
 * @author cr
 * @date 2022/11/16
 * @description
 */
public interface LoginService {
    UserDetails Login(MemberLoginVo memberLoginVo);
}
