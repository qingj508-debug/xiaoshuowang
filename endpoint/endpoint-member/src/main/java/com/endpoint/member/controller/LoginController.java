package com.endpoint.member.controller;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.common.utils.SmsCodeUtils;
import com.endpoint.member.constant.MemberConstant;
import com.endpoint.member.feign.ThirdPartFeignService;
import com.endpoint.member.service.LoginService;
import com.endpoint.member.service.RegisterService;
import com.endpoint.member.vo.MemberLoginVo;
import com.endpoint.member.vo.MemberRegistVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author cr
 * @date 2022/11/11
 * @description
 */
@Api(tags = "登录相关接口")
@RestController
@RequestMapping("/member/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录")
    @PostMapping
    public ResultBean register(@Valid @RequestBody MemberLoginVo loginVo){

        UserDetails userDetails = loginService.Login(loginVo);
        return ResultBean.ok(userDetails);
    }

}
