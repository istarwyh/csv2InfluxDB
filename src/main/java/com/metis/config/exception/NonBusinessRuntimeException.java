package com.metis.config.exception;

import lombok.Getter;

/**
 * @Description: NonBusinessRuntimeException 继承的是
 *               RuntimeException,指Java程序在运行时产生的由解释器引发的各种异常，不要求捕获
 * @Author: YiHui
 * @Date: 2021-01-30 22:25
 * @Version: ing
 */
@Getter
public class NonBusinessRuntimeException extends RuntimeException {
    /**
     * 异常码
     */
    private Integer code;
    /**
     * 异常提示信息
     */
    private String  message;

    public NonBusinessRuntimeException() {
        super(ExceptionMsgEnum.UNEXPECTED_EXCEPTION.getMsg());
    }

    public NonBusinessRuntimeException(ExceptionMsgEnum ee) {
        this.code = ee.getCode();
        this.message = ee.getMsg();
    }

    public NonBusinessRuntimeException(ExceptionMsgEnum ee, Throwable t) {
        super(t);
        this.code = ee.getCode();
        this.message = ee.getMsg();
    }
}
