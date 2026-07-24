package com.endpoint.member.controller;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.common.utils.SmsCodeUtils;
import com.endpoint.member.constant.MemberConstant;
import com.endpoint.member.entity.Member;
import com.endpoint.member.feign.ThirdPartFeignService;
import com.endpoint.member.service.RegisterService;
import com.endpoint.member.vo.MemberRegistVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cr
 * @date 2022/11/11
 * @description
 */
@Api(tags = "注册相关接口")
@RestController
@RequestMapping("/member/register")
public class RegisterController {

    @Autowired
    private ThirdPartFeignService thirdPartFeignService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RegisterService registerService;

    @GetMapping("/sms/sendCode")
    public ResultBean sendCode(@RequestParam("phone") String phone){
        // 从redis获取保存的验证码
        String redisCode = stringRedisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            // 如果验证码已经存在,判断该验证码存入的时间与当前时间是否小于60秒
            //if (System.currentTimeMillis() - Long.parseLong(redisCode.split("_")[1]) < 60000) {
                // 小于60秒不能再发
                return ResultBean.fail(ResponseStatus.SMS_CODE_EXCEPTION);
            //}
        }
        //生成验证码
        String code = SmsCodeUtils.randomCode();
        //reids中存入 验证码
        stringRedisTemplate.opsForValue()
                .set(MemberConstant.SMS_CODE_CACHE_PREFIX + phone,
                        code+"_"+System.currentTimeMillis(),10, TimeUnit.MINUTES);
        thirdPartFeignService.sendCode(phone,code);
        return ResultBean.ok();
    }

    @ApiOperation(value = "用户注册")
    @PostMapping
    public ResultBean register(@Valid @RequestBody MemberRegistVo registVo){
        // 将传递过来的验证码 与 存redis中的验证码进行比较
        String code = registVo.getVelCode();
        String value = stringRedisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX + registVo.getUsername());
        //如果该手机号已经发送验证码并且没有过期
        if (!StringUtils.isEmpty(value)) {
            // 验证码和redis中的一致
            if(code.equals(value.split("_")[0])) {
                // 校验成功删除验证码：令牌机制
                stringRedisTemplate.delete(MemberConstant.SMS_CODE_CACHE_PREFIX + registVo.getUsername());
                // 执行注册功能
                UserDetails userDetails = registerService.regist(registVo);
                return ResultBean.ok(userDetails);
            }else{
                //验证码与Redis中不一致
                return ResultBean.fail(ResponseStatus.VEL_CODE_ERROR);
            }
        }else{
            //验证码已过期,需要重新发送
            return ResultBean.fail(ResponseStatus.VEL_CODE_ERROR);
        }
    }
}
