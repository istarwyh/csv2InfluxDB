package com.metis.annotation.log;

import java.lang.annotation.*;

/**
 * @Description: ExecutionTime
 * @Author: YiHui
 * @Date: 2021-02-04 19:48
 * @Version: ing
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
}