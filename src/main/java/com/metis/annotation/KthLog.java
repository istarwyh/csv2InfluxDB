package com.metis.annotation;

import java.lang.annotation.*;

/**
 * @author wangyihui
 * 注意因为ComponentScan的关系，KthLog必须在
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KthLog {
    String value() default "";
}
