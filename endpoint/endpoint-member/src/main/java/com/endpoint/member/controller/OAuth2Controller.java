package com.endpoint.member.controller;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.HttpUtils;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.service.OAuth2Service;
import com.endpoint.member.vo.SocialUserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cr
 * @date 2022/11/18
 * @description
 */
@Api(tags = "社交登录相关接口")
@RestController
@RequestMapping("/member/oauth2")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oAuth2Service;


    @GetMapping("/weibo/login/success")
    public ResultBean weibo(@RequestParam("code") String code) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("client_id", "504454291");
        map.put("client_secret", "27f6b770f0fa38debe1686b4c4cbb4cb");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://192.168.0.107:9999/#/oauth/login/weibo");
        map.put("code", code);
        HttpResponse post = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<String, String>(), map, new HashMap<String, String>());
        if (post.getStatusLine().getStatusCode() == 200) {
            //如果返回状态码是200 执行成功了
            //获取返回的授权令牌  通过方法将返回的令牌对象 转为 json格式字符串
            String json = EntityUtils.toString(post.getEntity());
            //将json格式字符串 转为自定义的 SocialUserVo 对象

            SocialUserVo socialUserVo = new ObjectMapper().readValue(json, SocialUserVo.class);
            //调用微博社交登录方法
            UserDetails user = oAuth2Service.oauthLogin(socialUserVo);

            if (user != null) {
                //正常登录 将用户信息放入session
                return ResultBean.ok(user);
            } else {
                //如果为空返送的 请求 没有正常相应 重新引导到登录页面
                return ResultBean.fail(ResponseStatus.OAUTH_LOGIN_EXCEPTION);
            }
        } else {
            return ResultBean.fail(ResponseStatus.OAUTH_LOGIN_EXCEPTION);
        }
    }
}