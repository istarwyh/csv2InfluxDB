package com.metis.annotation;

import java.lang.annotation.*;

/**
 * @author sx_wangyihui
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KthLog {
    String value() default "";
}
