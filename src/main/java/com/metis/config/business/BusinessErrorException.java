package com.metis.config.business;

import lombok.Getter;

/**
 * @Description: BusinessErrorException
 * @Author: YiHui
 * @Date: 2021-01-30 22:25
 * @Version: ing
 */
@Getter
public class BusinessErrorException extends RuntimeException {
    /**
     * 异常码
     */
    private final String code;
    /**
     * 异常提示信息
     */
    private final String message;

    public BusinessErrorException(BusinessMsgEnum businessMsgEnum) {
        this.code = businessMsgEnum.code();
        this.message = businessMsgEnum.msg();
    }
}
