package com.endpoint.member.service;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.member.vo.SocialUserVo;

/**
 * @author cr
 * @date 2022/11/18
 * @description
 */
public interface OAuth2Service {
    UserDetails oauthLogin(SocialUserVo socialUserVo);
}
