package com.metis.config;

/**
 * 泛型使所有的返回值类型都可以使用该统一结构，在具体的场景将泛型替换成具体的数据类型即可
 * 对状态码和提示信息可以定义一个枚举类型
 * @param <T>
 */
public class JsonResult<T> {
    private T data;
    private final Integer code;
    private final String msg;

    /**
     * 若没有数据返回，默认状态码为0，提示信息为：操作成功！
     */
    public JsonResult() {
        this.code = StatusEnum.SUCCESS.getCode() ;
        this.msg = StatusEnum.SUCCESS.getDesc();
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = StatusEnum.SUCCESS.getCode();
        this.msg = StatusEnum.SUCCESS.getDesc();
    }

    /**
     * 有数据返回，状态码为1，人为指定错误提示信息
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = StatusEnum.FAILURE.getCode();
        this.msg = msg;
    }
// 省略get和set方法
}