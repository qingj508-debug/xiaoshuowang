package com.endpoint.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.endpoint.common.valid.AddGroup;
import com.endpoint.common.valid.UpdateGroup;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Data
@ApiModel(description = "会员信息")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "修改必须指定会员id",groups = {UpdateGroup.class})
    @Null(message = "新增不能指定id",groups = {AddGroup.class})
    private Long id;

    /**
     * 登录名
     */
    @NotBlank(message="手机号不能为空！",groups = {AddGroup.class,UpdateGroup.class})
    @Pattern(regexp="^1[3|4|5|6|7|8|9][0-9]{9}$",message="手机号格式不正确！",groups = {AddGroup.class,UpdateGroup.class})
    private String username;

    /**
     * 登录密码
     */
    @NotEmpty(message = "密码必须填写")
    @Length(min = 6,max = 20,message = "密码必须是6-20位字符")
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
