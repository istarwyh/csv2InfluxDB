package com.metis.common.designpattern.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionEnum {

    ADD(1, "加"), CUT(2, "减"), MULTIPLY(3, "乘");

    /**
     * 返回状态码
     */
    private final Integer code;

    /**
     * 返回描述
     */
    private final String desc;

    public static ConditionEnum of(Integer source) {
        if (source == null) {
            return null;
        }
        for (ConditionEnum value : values()) {
            if (value.code == source) {
                return value;
            }
        }
        throw new RuntimeException("not found Condition: " + source);
    }

    /**
     * 模拟业务的控制逻辑
     */
    public static ConditionEnum judgeBy(Integer source) {
        if(door1(source)){
            return ADD;
        }
        if(door2(source)){
            return CUT;
        }
        if(door3(source)){
            return MULTIPLY;
        }
        throw new RuntimeException("not judge Condition: " + source);

    }

    /**
     * "门"中可以封装很复杂的业务逻辑，以判断输入的参数是否足以开门
     */
    private static  boolean door1(int flag){
        return flag > 1;
    }

    private static boolean door2(int flag){
        return flag < 1;
    }

    private static boolean door3(int flag){
        return flag == 1;
    }

}
