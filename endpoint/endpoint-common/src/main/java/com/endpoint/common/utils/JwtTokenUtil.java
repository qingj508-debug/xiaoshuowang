package com.endpoint.common.utils;

import com.endpoint.common.bean.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author xiongxiaoyang
 * @version 1.0
 * @since 2020/5/27
 */
@Slf4j
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String SECRET = "SARTa1235512ssssd";
    private static final long EXPIRATION = 1000 * 60 * 60 ;


    /**
     * 根据负责生成JWT的token
     */
    public static  String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(createExpirationDate())//设置过期时间
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims =null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    public static Date createExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION);
    }

    /**
     * 从token中获取用户信息
     */
    public static Long getUserIdFromToken(String token) {
        if(isTokenExpired(token)){
            return null;
        }
        Long userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = Long.parseLong(claims.getSubject());
            return userId;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }


    /**
     * 判断token是否已经失效
     */
    public static boolean  isTokenExpired(String token) {
        if(StringUtils.isEmpty(token)){
            return true;
        }
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }





    /**
     * 从token中获取过期时间
     */
    private static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();

    }

    /**
     * 根据用户信息生成token
     */
    @SneakyThrows
    public static String createToken(Long userId) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USERNAME, userId.toString());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public static boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public static String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

}
