package com.metis.config.exception;

import com.metis.config.StatusEnum;

import lombok.AllArgsConstructor;

/**
 * @Description: exceptionEnum
 * @Author: YiHui
 * @Date: 2021-01-30 21:53
 * @Version: ing
 */
@AllArgsConstructor
public enum ExceptionEnum {
    /**
     * 中间状态,需要处理-->最终状态
     */
    LACK_PARAM(40000,"缺少必要的请求参数"),
    /**
     * 中间状态,需要处理-->最终状态
     */
    NULL_VALUE(50000,"数据不正常为null");
    /**
     * 返回状态码
     */
    private final int code;
    /**
     * 返回描述
     */
    private final String desc;

    public int code(){
        return this.code;
    }

    public String getDesc(){
        return this.desc;
    }
    public static String getDescByCode(Integer code){
        if( code == null ){
            return "无";
        }
        for( StatusEnum status : StatusEnum.values() ){
            if( status.getCode().equals( code )){
                return status.getDesc();
            }
        }
        return StatusEnum.UNKNOWN_ERROR.getDesc();
    }
}
