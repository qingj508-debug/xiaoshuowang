package com.endpoint.common.exception;

import com.endpoint.common.constant.ResponseStatus;
import lombok.Data;

/**
 * 自定义业务异常，用于处理用户请求时，业务错误时抛出
 */
@Data
public class BusinessException extends RuntimeException {
    private ResponseStatus resStatus;
    public BusinessException(ResponseStatus resStatus) {
        //构造器之间的调用必须在第一行
        super(resStatus.getMsg());
        this.resStatus = resStatus;
    }
}
