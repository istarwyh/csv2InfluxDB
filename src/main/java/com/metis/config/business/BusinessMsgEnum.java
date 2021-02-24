package com.metis.config.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常提示信息枚举类
 * @author shengwu ni
 */
@Getter
@AllArgsConstructor
public enum BusinessMsgEnum {
    /** 参数异常 */
    PARAM_EXCEPTION("102", "参数异常!"),
    /** 等待超时 */
    SERVICE_TIME_OUT("103", "服务调用超时！"),
    /** 参数过大 */
    PARAM_BIG_EXCEPTION("102", "输入的图片数量不能超过50张!"),
    /** 500 : 一劳永逸的提示也可以在这定义 */
    UNEXPECTED_EXCEPTION("50000", "系统发生异常，请联系管理员！");
    /**
     * 消息码
     */
    private final String code;
    /**
     * 消息内容
     */
    private final String msg;

    public String code() {
        return this.code;
    }
    public String msg(){
        return this.msg;
    }
}
