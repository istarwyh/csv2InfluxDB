package com.metis.config.business;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: BusinessType
 * @Author: YiHui
 * @Date: 2021-01-30 15:40
 * @Version: ing
 */
@Getter
@AllArgsConstructor
public enum BusinessType {
    /**
     * 最终状态
     */
    INSERT(1,"增加"),
    /**
     * 最终状态
     */
    DELETE(2,"删除"),
    /**
     * 最终状态
     */
    UPDATE(3,"更新"),
    /**
     * 最终状态
     */
    SELECT(4,"查询"),
    /**
     * 最终状态
     */
    ADD(11,"批量增加"),
    /**
     * 最终状态
     */
    REMOVE(22,"批量删除"),
    /**
     * 最终状态
     */
    EDIT(33,"批量更改"),
    /**
     * 最终状态
     */
    QUERY(44,"批量查询"),
    /**
     * 初始状态
     */
    UNKNOWN_BEHAVIOR(0,"未指定业务操作");

    /**
     * 返回状态码
     */
    private final Integer code;
    /**
     * 返回描述
     */
    private final String desc;
}
