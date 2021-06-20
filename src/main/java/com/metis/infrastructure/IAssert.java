package com.metis.infrastructure;

import com.metis.config.exception.NonBusinessRuntimeException;
import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Supplier;

/**
 * @Description: IAssert
 * @Author: wx:istarwyh
 * @Date: 2021-05-28 11:22
 * @Version: ing
 */

public interface IAssert {
    /**
     * 创建新的异常
     *
     * @return
     */
    NonBusinessRuntimeException newException();

    /**
     * 根据抛出异常类和ErrorCode创建新的异常
     *
     * @param t
     * @return
     */
    NonBusinessRuntimeException newException(Throwable t);

    /**
     * 对象为空则抛出异常
     *
     * @param object
     */
    default void assertNotNull(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            throw newException();
        }
    }

    /**
     * 对象为空则抛出异常
     */
    default void assertNotNull(Object object, Object o2) {
        if (ObjectUtils.isEmpty(object) || ObjectUtils.isEmpty(o2)) {
            throw newException();
        }
    }

    /**
     * 对象为空则抛出异常
     *
     * @param object
     * @param t
     */
    default void assertNotNull(Object object, Throwable t) {
        if (ObjectUtils.isEmpty(object)) {
            throw newException(t);
        }
    }

    /**
     * 对象不为空则抛出异常
     *
     * @param object
     */
    default void assertNull(Object object) {
        if (!ObjectUtils.isEmpty(object)) {
            throw newException();
        }
    }

    /**
     * 对象不为空则抛出异常
     *
     * @param object
     * @param t
     */
    default void assertNull(Object object, Throwable t) {
        if (!ObjectUtils.isEmpty(object)) {
            throw newException(t);
        }
    }

    /**
     * lambda函数判断为true则抛出异常
     *
     * @param supplier
     */
    default void assertTrueLambda(Supplier<Boolean> supplier) {
        if (supplier.get()) {
            throw newException();
        }
    }

    /**
     * lambda函数判断为true则抛出异常
     *
     * @param supplier
     * @param t
     */
    default void assertTrueLambda(Supplier<Boolean> supplier, Throwable t) {
        if (supplier.get()) {
            throw newException(t);
        }
    }

    /**
     * lambda函数判断为true则抛出异常
     *
     * @param supplier
     */
    default void assertFalseLambda(Supplier<Boolean> supplier) {
        if (!supplier.get()) {
            throw newException();
        }
    }

    /**
     * lambda函数判断为true则抛出异常
     *
     * @param supplier
     * @param t
     */
    default void assertFalseLambda(Supplier<Boolean> supplier, Throwable t) {
        if (!supplier.get()) {
            throw newException(t);
        }
    }
}
