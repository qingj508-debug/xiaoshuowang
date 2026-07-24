package com.endpoint.gateway.filter;

import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.JwtTokenUtil;
import com.endpoint.common.utils.ResultBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author cr
 * @date 2022/11/15
 * @description
 */
@Component
public class LoginFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取request请求对象
        ServerHttpRequest request = exchange.getRequest();
        //2.获取请求路径
        String requestURI = request.getURI().getPath();
        //3.如果是来 注册 登录 及使用swagger的直接方行
        if(requestURI.contains("/play/")||requestURI.contains("/member/register")||requestURI.contains("/member/login")
                || requestURI.contains("/home")  || requestURI.contains("/search")
                || requestURI.contains("/oauth2")
                || requestURI.contains("/book/bookCategory")
                || requestURI.contains("/book/getBookById") || requestURI.contains("/lastChapter/about")
                || requestURI.contains("/book/recList") || requestURI.contains("/comment/newestList")
                || requestURI.contains("/book/bookContent/content") || requestURI.contains("/book/bookIndex/chapterList")
                || requestURI.contains("/swagger-ui.html")
                || requestURI.contains("/v2/api-docs")
                || requestURI.contains("/swagger-resources/configuration/ui")
                || requestURI.contains("/swagger-resources")
                || requestURI.contains("/swagger-resources/configuration/security")
                || requestURI.contains("/thirdparty/orderPay/notify")
                || requestURI.contains("/isAddToBookshelf") || requestURI.contains("addReadHistory")
                || requestURI.contains("addVisitCount")
        ){
            return chain.filter(exchange);
        }

        //取出头信息
        HttpHeaders headers = request.getHeaders();
        List<String> authorizations = headers.get("Authorization");

        if(authorizations==null ||authorizations.size()<=0){
            //如果请求头不包含Authorization 那么拒绝访问
            return access_denied(exchange.getResponse(),ResponseStatus.NO_LOGIN);
        }
        // 获取 token
        String token = authorizations.get(0);
        if(StringUtils.isEmpty(token)){
            //token为空 访问拒绝
            return access_denied(exchange.getResponse(),ResponseStatus.NO_LOGIN);
        }

        if(JwtTokenUtil.isTokenExpired(token)){
            //token 过期 访问拒绝
            return access_denied(exchange.getResponse(),ResponseStatus.NO_LOGIN);
        }

        return chain.filter(exchange);
    }

    /**
     * 拒绝访问
     */
    private Mono<Void> access_denied(ServerHttpResponse response,  ResponseStatus responseStatus){
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Flux<DataBuffer> result = Flux.just(getBodyBuffer(response, ResultBean.fail(responseStatus)));
        return response.writeWith(result);
    }
    @SneakyThrows
    private DataBuffer getBodyBuffer(ServerHttpResponse response, ResultBean resultBean) {
        byte[] bytes = new ObjectMapper().writeValueAsBytes(resultBean);
        return response.bufferFactory().wrap(bytes);
    }
}
