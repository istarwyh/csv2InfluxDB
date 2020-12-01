package com.metis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 成功
     */
    SUCCESS(0,"操作成功"),
    /**
     * 失败
     */
    FAILURE(1,"操作失败");
    /**
     * 返回状态码
     */
    private final Integer code;
    /**
     * 返回描述
     */
    private final String desc;

    public static String getDescByCode(Integer code){
        if( code == null ){
            return "无";
        }
        for( StatusEnum status : StatusEnum.values() ){
            if( status.getCode().equals( code )){
                return status.getDesc();
            }
        }
        return "未知的错误";
    }

}
