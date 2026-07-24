package com.endpoint.member.service;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.member.vo.MemberRegistVo;

/**
 * @author cr
 * @date 2022/11/14
 * @description
 */
public interface RegisterService {
    UserDetails regist(MemberRegistVo memberRegistVo);
}
