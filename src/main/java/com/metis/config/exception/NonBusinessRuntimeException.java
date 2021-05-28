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
    private final String code;
    /**
     * 异常提示信息
     */
    private final String message;

    public NonBusinessRuntimeException(ExceptionMsgEnum ee) {
        this.code = ee.getCode();
        this.message = ee.getMsg();
    }
}
