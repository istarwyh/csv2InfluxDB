package com.metis.annotation;

import java.lang.annotation.*;

/**
 * @Description: DuringTime
 * @Author: YiHui
 * @Date: 2021-01-15 01:03
 * @Version: ing
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DuringTime {
    String value() default "这里居然空空如也";
}
