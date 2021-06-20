package com.metis.config.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * to define the corresponding enum of functional status beyond http status code
 * 
 * @author MBin_王艺辉istarwyh
 */
@Getter
@AllArgsConstructor
public enum BusinessStatusEnum {
    /**
     * 成功,最终状态
     */
    SUCCESS(20000, "操作成功"),
    /**
     * 失败,最终状态
     */
    FAILURE(99999, "操作失败"),
    /**
     * 中间状态,需要处理-->最终状态
     */
    LACK_PARAM(40000, "缺少必要的请求参数"),
    /**
     * 中间状态,需要处理-->参数过大
     */
    PARAM_BIG_EXCEPTION(40002, "输入的图片数量不能超过50张!"),
    /**
     * 未知错误,中间状态,需要处理-->最终状态
     */
    UNKNOWN_ERROR(44444, "未知错误");

    /**
     * 返回状态码
     */
    private final Integer code;
    /**
     * 返回描述
     */
    private final String  desc;

    public static String getDescByCode(Integer code) {
        if (code == null) {
            return "null";
        }
        return Arrays.stream(BusinessStatusEnum.values()).filter(status -> status.getCode().equals(code)).findFirst()
                .orElse(BusinessStatusEnum.UNKNOWN_ERROR).getDesc();
    }
}
