package com.endpoint.member.exception;


import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.exception.BusinessException;
import com.endpoint.common.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的异常处理器
 * */
@Slf4j
@RestControllerAdvice(basePackages = "com.endpoint.member.controller")
public class EndpointExceptionAdvice {

    /**
     * 处理业务异常
     * */
    @ExceptionHandler(BusinessException.class)
    public ResultBean handlerBusinessException(BusinessException e){
        log.error(e.getMessage(),e);
        return ResultBean.fail(e.getResStatus());
    }
    //编写异常处理方法
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{},异常类型{}",e.getMessage(),e.getClass());
        BindingResult result = e.getBindingResult();
        Map<String,String> errorMap=new HashMap<>();
        result.getFieldErrors().forEach((item)->{
            String field = item.getField();
            String message = item.getDefaultMessage();
            errorMap.put(field,message);
        });
        return ResultBean.fail(ResponseStatus.PARAM_ERROR,errorMap);
    }
    /**
     * 处理系统异常
     * */
    @ExceptionHandler(Exception.class)
    public ResultBean handlerException(Exception e){
        log.error(e.getMessage(),e);
        return ResultBean.error();
    }
}
