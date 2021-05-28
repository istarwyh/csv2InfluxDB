package com.metis.dto;

import com.metis.config.business.BusinessStatusEnum;
import com.metis.config.exception.ExceptionMsgEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 泛型使所有的返回值类型都可以使用该统一结构，在具体的场景将泛型替换成具体的数据类型即可 对状态码和提示信息可以定义一个枚举类型
 * 
 * @author istarwyh
 * @param <T> 如果不加@Getter,那么JsonResult中的属性不能从外面访问,那么JsonResult不能被自动转成json
 *            而没有@Setter,只是没有了提供修改的口子 有了@SuperBuilder
 *            允许即使继承父类也可以使用lambda,连new都隐藏起来了;注意Builder依赖全参构造器
 */
@Getter
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {
    private final Integer code;
    private final String  msg;
    private T             data;

    /**
     * 若没有数据返回，默认状态码为0，提示信息为：操作成功！
     */
    public ResponseDTO() {
        this.code = BusinessStatusEnum.SUCCESS.getCode();
        this.msg = BusinessStatusEnum.SUCCESS.getDesc();
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public ResponseDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为BusinessStatusEnum.SUCCESS.getCode()，默认提示信息为：操作成功！
     */
    public ResponseDTO(T data) {
        this.data = data;
        this.code = BusinessStatusEnum.SUCCESS.getCode();
        this.msg = BusinessStatusEnum.SUCCESS.getDesc();
    }

    /**
     * 有数据返回但是错误，设定错误状态码为BusinessStatusEnum.FAILURE.getCode()，人为指定错误提示信息
     */
    public ResponseDTO(T data, String msg) {
        this.data = data;
        this.code = BusinessStatusEnum.FAILURE.getCode();
        this.msg = msg;
    }

    public static <T> ResponseDTO<T> forLackParam() {
        return new ResponseDTO<>(BusinessStatusEnum.LACK_PARAM.getCode(), BusinessStatusEnum.LACK_PARAM.getDesc());
    }

    public boolean isOk() {
        return BusinessStatusEnum.SUCCESS.getCode().equals(code);
    }

    public static <T> ResponseDTO<T> forNullValue() {
        return new ResponseDTO<>(ExceptionMsgEnum.NULL_VALUE.getCode(), ExceptionMsgEnum.NULL_VALUE.getMsg());
    }

    public static <T> ResponseDTO<T> ofOriginal(Integer code, String message) {
        return new ResponseDTO<>(code, message);
    }

    @Override
    public String toString() {
        return "{" + "\"data\":\"" + data + "\", \"code\":\"" + code + "\", \"msg\":\"" + msg + "\"}";
    }
}
