package com.metis.config.exception;

import com.metis.infrastructure.IAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 非用户操作导致的异常提示信息枚举类 一般需要开发人员解决
 * 
 * @author shengwu ni
 */
@Getter
@AllArgsConstructor
public enum ExceptionMsgEnum implements IAssert {
    /** 参数异常 */
    PARAM_EXCEPTION(501, "参数异常!"),
    /** 等待超时 */
    SERVICE_TIME_OUT(502, "服务调用超时！"),
    /** 空指针异常 */
    NULL_VALUE(503, "出现了不正常的null值"),
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


    public NonBusinessRuntimeException getException() {
        return new NonBusinessRuntimeException(this);
    }

    /**
     * 创建新的异常
     *
     * @return
     */
    @Override
    public NonBusinessRuntimeException newException() {
        return new NonBusinessRuntimeException(this);
    }

    /**
     * 根据抛出异常类和ErrorCode创建新的异常
     *
     * @param t
     * @return
     */
    @Override
    public NonBusinessRuntimeException newException(Throwable t) {
        return new NonBusinessRuntimeException(this, t);
    }

}
