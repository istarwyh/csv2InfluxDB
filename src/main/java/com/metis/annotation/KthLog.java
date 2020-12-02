package com.metis.annotation;

import java.lang.annotation.*;

/**
 * @author wangyihui
 * 注意因为ComponentScan的关系，KthLog必须在annotation目录下？
 * 这个注解只能放在方法上： @Target(ElementType.METHOD)
 * 这个注解要加载到 JVM 内存：@Retention
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KthLog {
    String value() default "这里居然空空如也";
}
