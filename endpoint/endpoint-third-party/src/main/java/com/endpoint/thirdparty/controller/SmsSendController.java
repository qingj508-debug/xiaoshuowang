package com.endpoint.thirdparty.controller;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.thirdparty.utils.AliyunSmsComponent;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cr
 * @date 2022/11/11
 * @description
 */
@Api(tags ="短信接口" )
@RestController
@RequestMapping("sms")
public class SmsSendController {
    @Autowired
    AliyunSmsComponent aliyunSmsComponent;
    //发送短信验证码，供其他服务调用（登录/注册验证码，走阿里云短信）
    @GetMapping("/sendCode")
    @ResponseBody
    public ResultBean sendCode(@RequestParam("phone") String phone, @RequestParam("code")String code){
        aliyunSmsComponent.sendSmsCode(phone, code);
        return ResultBean.ok();
    }
}
