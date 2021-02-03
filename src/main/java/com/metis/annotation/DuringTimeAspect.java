package com.metis.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: DuringTimeAspect
 * @Author: YiHui
 * @Date: 2021-01-15 01:04
 * @Version: ing
 */
@Component
@Aspect
public class DuringTimeAspect {
    private static final Logger logger = LoggerFactory.getLogger(DuringTimeAspect.class);
    long startTime = 0;

    @Pointcut("@annotation(com.metis.annotation.DuringTime)")
    private void pointcut() { }

    @Before("pointcut()")
    public void advice(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        logger.info("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-方法开始于[" + new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒").format(new Date(startTime)) + "]");
    }

    @After("pointcut()")
    public void then(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        logger.info("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-方法经过了[" + (endTime - startTime)+ "ms]");
    }
}
