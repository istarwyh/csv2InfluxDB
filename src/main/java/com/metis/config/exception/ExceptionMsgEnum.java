package com.metis.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 非用户操作导致的异常提示信息枚举类 一般需要开发人员解决
 * 
 * @author shengwu ni
 */
@Getter
@AllArgsConstructor
public enum ExceptionMsgEnum {
    /** 参数异常 */
    PARAM_EXCEPTION(102, "参数异常!"),
    /** 等待超时 */
    SERVICE_TIME_OUT(103, "服务调用超时！"),
    /** 空指针异常 */
    NULL_VALUE(501, "数据不正常为null"),
    /** 500 : 一劳永逸的提示也可以在这定义 */
    UNEXPECTED_EXCEPTION(500, "未知错误,请联系管理员！");

    /**
     * 消息码
     */
    private final Integer code;
    /**
     * 消息内容
     */
    private final String  msg;
}
