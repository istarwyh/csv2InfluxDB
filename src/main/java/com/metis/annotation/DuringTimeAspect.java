package com.metis.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: DuringTimeAspect
 * @Author: YiHui
 * @Date: 2021-01-15 01:04
 * @Version: ing
 */
@Component
@Aspect
public class DuringTimeAspect {

    @Pointcut("@annotation(com.metis.annotation.DuringTime)")
    private void pointcut() { }

    @Before("pointcut()")
    public void advice(JoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        System.out.println("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-方法开始时间-[" + startTime + "]");
    }

    @After("pointcut()")
    public void then(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        System.out.println("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-方法结束时间-[" + endTime + "]");
    }
}
