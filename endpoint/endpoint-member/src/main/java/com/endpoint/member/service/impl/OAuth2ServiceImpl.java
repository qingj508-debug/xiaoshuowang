package com.endpoint.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.utils.HttpUtils;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.member.entity.Member;
import com.endpoint.member.service.IMemberService;
import com.endpoint.member.service.OAuth2Service;
import com.endpoint.member.vo.SocialUserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cr
 * @date 2022/11/18
 * @description
 */
@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    @Autowired
    private IMemberService memberService;

    @Override
    public UserDetails oauthLogin(SocialUserVo socialUserVo) {
        String uid = socialUserVo.getUid();
        //判断当前 社交登录用户是否 登录过系统
        Member member = memberService.getOne(new QueryWrapper<Member>().eq("social_uid", uid));
        if(member!=null){
            member.setSocialUid(socialUserVo.getUid());
            member.setAccessToken(socialUserVo.getAccess_token());
            member.setExpiresIn(socialUserVo.getExpires_in());
            member.setUpdateTime(LocalDateTime.now());
            //更新用户 社交登录数据
            memberService.updateById(member);

            UserDetails userDetails = new UserDetails();
            //生成 jwtToken
            String token = JwtTokenUtil.createToken(member.getId());
            userDetails.setId(member.getId());
            userDetails.setNickName(member.getNickName());
            userDetails.setToken(token);
            return userDetails;
        }else{
            //第一次社交登录保存用户信息到 member
            member = new Member();
            try {
                // 查询当前社交用户的社交账号信息
                Map<String,String> query = new HashMap<>();
                query.put("access_token",socialUserVo.getAccess_token());
                query.put("uid",uid);
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String,String>(), query);
                if(response.getStatusLine().getStatusCode() == 200) {
                    // 查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    Map<String,String> map =new ObjectMapper().readValue(json, Map.class);
                    // 昵称
                    String nickName = map.get("name");
                    member.setNickName(nickName);
                    // 性别
                    String gender = map.get("gender");
                    member.setGender("m".equals(gender) ? 1 : 0);
                    //头像
                    String  header =  map.get("profile_image_url");
                    member.setHeader(header);
                    //填充其他数据
                    member.setSocialUid(socialUserVo.getUid());
                    member.setAccessToken(socialUserVo.getAccess_token());
                    member.setExpiresIn(socialUserVo.getExpires_in());
                    member.setCreateTime(LocalDateTime.now());
                    member.setUpdateTime(LocalDateTime.now());
                    memberService.save(member);

                    //保存成功生成令牌
                    UserDetails userDetails = new UserDetails();
                    //生成 jwtToken
                    String token = JwtTokenUtil.createToken(member.getId());
                    userDetails.setId(member.getId());
                    userDetails.setNickName(member.getNickName());
                    userDetails.setToken(token);
                    return userDetails;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
