package com.metis.annotation;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import java.lang.reflect.Method;

/**
 * @Description: TraceAdvice
 * @Author: YiHui
 * @Date: 2021-01-18 21:51
 * @Version: ing
 */
public class TraceAdvice {
    public static Tracer.Span span = null;

    public static void getCurrentSpan() {
        if (span == null) {
            span = Tracer.newTracer().newSpan();
        }
    }

    /**
     * @param target 目标类实例
     * @param clazz  目标类class
     * @param method 目标方法
     * @param args   目标方法参数
     */
    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.This(optional = true) Object target,
                                     @Advice.Origin Class<?> clazz,
                                     @Advice.Origin Method method,
                                     @Advice.AllArguments Object[] args) {
        getCurrentSpan();
        span.start();

    }

    /**
     * @param target 目标类实例
     * @param clazz  目标类class
     * @param method 目标方法
     * @param args   目标方法参数
     * @param result 返回结果
     */
    @Advice.OnMethodExit(onThrowable = Throwable.class)
    public static void onMethodExit(@Advice.This(optional = true) Object target,
                                    @Advice.Origin Class<?> clazz,
                                    @Advice.Origin Method method,
                                    @Advice.AllArguments Object[] args,
                                    @Advice.Return(typing = Assigner.Typing.DYNAMIC) Object result) {
        span.end();
        span = null;

    }
}
