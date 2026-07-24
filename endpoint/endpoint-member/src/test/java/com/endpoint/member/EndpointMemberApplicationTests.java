package com.endpoint.member;

import com.endpoint.common.bean.UserDetails;
import com.endpoint.common.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class EndpointMemberApplicationTests {

    @Test
    void contextLoads() {
        String token = JwtTokenUtil.createToken(1L);
        //创建token字符串
        System.out.println(token);
    }




    @Test
    void getUserId(){
        //通过token字符串获取 用户信息对象
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2Njg1ODY2NTUsInN1YiI6IjEiLCJjcmVhdGVkIjoxNjY4NTgzMDU1Njk2fQ.PSNVBP_KSFY2js878Dst5SKJoeG-eZC8hKhV6zknNf6eFE1uvdscGQDS3P-eRwlQNQvqBGoU8KYuXEiKdyxunw";
        Long userId = JwtTokenUtil.getUserIdFromToken(token);
        System.out.println(userId);

    }
    @Test
    void getClaimsFromToken(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2Njg1ODY2NTUsInN1YiI6IjEiLCJjcmVhdGVkIjoxNjY4NTgzMDU1Njk2fQ.PSNVBP_KSFY2js878Dst5SKJoeG-eZC8hKhV6zknNf6eFE1uvdscGQDS3P-eRwlQNQvqBGoU8KYuXEiKdyxunw";
        Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(token);
        Long created = (Long) claimsFromToken.get("created");
        //打印token创建时间
        System.out.println(new Date(created));
    }

}
