package com.metis.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * to define the corresponding enum of functional status beyond http status code
 * @author MBin_王艺辉istarwyh
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    /**
     * 成功,最终状态
     */
    SUCCESS(20000,"操作成功"),
    /**
     * 失败,最终状态
     */
    FAILURE(99999,"操作失败"),
    /**
     * 未知错误,中间状态,需要处理-->最终状态
     */
    UNKNOWN_ERROR(44444,"未知错误");
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
        return StatusEnum.UNKNOWN_ERROR.getDesc();
    }
}
