package com.endpoint.common.bean;

import lombok.Data;
import lombok.ToString;

/**
 * 登陆用户信息封装
 * @author xiongxiaoyang
 * @version 1.0
 * @since 2020/5/27
 */
@Data
@ToString
public class UserDetails {

    private Long id;

    private String token;

    private String nickName;
}
