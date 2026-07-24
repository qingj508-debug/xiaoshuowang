package com.endpoint.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author cr
 * @date 2022/11/14
 * @description
 */
@Data
public class MemberRegistVo {
    @NotBlank(message="手机号不能为空！")
    @Pattern(regexp="^1[3|4|5|6|7|8|9][0-9]{9}$",message="手机号格式不正确！")
    private String username;

    @NotEmpty(message = "密码必须填写")
    @Length(min = 6,max = 20,message = "密码必须是6-20位字符")
    private String password;

    @NotEmpty(message = "验证码必须填写")
    private String velCode;
}
