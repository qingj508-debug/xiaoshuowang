package com.endpoint.book.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.endpoint.common.valid.AddGroup;
import com.endpoint.common.valid.UpdateGroup;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.checkerframework.checker.units.qual.Length;
import org.intellij.lang.annotations.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/11/24
 * @description
 */
@Data
public class MemberTo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */

    private Long id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 用户头像
     */
    private String header;
    /**
     * 用户性别，0：男，1：女
     */
    private Integer gender;
    /**
     * 账户余额
     */
    private Long accountBalance;
    /**
     * 用户状态，0：正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    /**
     * 社交账号唯一id
     */
    private String socialUid;
    /**
     * 社交账号访问令牌
     */
    private String accessToken;
    /**
     * 社交账号访问令牌过期时间
     */
    private Long expiresIn;

}