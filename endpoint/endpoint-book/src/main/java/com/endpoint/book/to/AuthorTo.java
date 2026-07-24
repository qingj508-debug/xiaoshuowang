package com.endpoint.book.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/12/8
 * @description
 */
@Data
public class AuthorTo {
    //主键
    private Long id;
    //用户ID
    private Long memberId;
    //邀请码
    private String inviteCode;
    //笔名
    private String penName;
    //手机号码
    private String telPhone;
    //QQ或微信账号
    private String chatAccount;
    //电子邮箱
    private String email;
    //作品方向，0：男频，1：女频
    private Integer workDirection;
    //0：正常，1：封禁
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
}
