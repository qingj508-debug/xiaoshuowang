package com.endpoint.common.utils;


import com.endpoint.common.constant.ResponseStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义Api响应结构
 * @param <T> 响应数据类型
 */
@Data
public class ResultBean<T>  {

    private int code = ResponseStatus.OK.getCode();

    /**
     * 响应消息
     * */
    private String msg = ResponseStatus.OK.getMsg();
    /**
     * 响应中的数据
     * */
    private T data;

    private ResultBean() {

    }

    private ResultBean(ResponseStatus ResponseStatus) {
        this.code = ResponseStatus.getCode();;
        this.msg = ResponseStatus.getMsg();
    }

    private ResultBean(T data) {
        this.data = data;
    }

    private ResultBean(ResponseStatus ResponseStatus,T data) {
        this.code = ResponseStatus.getCode();;
        this.msg = ResponseStatus.getMsg();
        this.data = data;
    }
    /**
     * 业务处理成功,无数据返回
     * */
    public static ResultBean<Void> ok() {
        return new ResultBean<>();
    }

    /**
     * 业务处理成功，有数据返回
     * */
    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<>(data);
    }

    /**
     * 业务处理失败
     * */
    public static ResultBean<Void> fail(ResponseStatus ResponseStatus) {
        return new ResultBean<>(ResponseStatus);
    }

    public static <T>ResultBean<T> fail(ResponseStatus ResponseStatus,T data){
        return new ResultBean<>(ResponseStatus,data);
    }


    /**
     * 系统错误
     * */
    public static ResultBean<Void> error() {
        return new ResultBean<>(ResponseStatus.ERROR);
    }
}

