package com.metis.annotation.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @Description: DuringTimeAspect
 * @Author: YiHui
 * @Date: 2021-01-15 01:04
 * @Version: ing
 */
@Component
@Aspect
public class ExeTimeLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ExeTimeLogAspect.class);
    private final StopWatch stopWatch = new StopWatch();

    @Pointcut("@annotation(com.metis.annotation.log.ExeTimeLog)")
    private void pointcut() { }

    @Before("pointcut()")
    public void advice() {
        stopWatch.start();
//        the following instruction cost average 2ms
//        logger.info("["
//                + joinPoint.getSignature().getDeclaringType().getSimpleName()
//                + "][" + joinPoint.getSignature().getName()
//                + "]-方法开始于[" + new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒").format(new Date()) + "]");
    }

    @After("pointcut()")
    public void then(JoinPoint joinPoint) {
        stopWatch.stop();
        long exeTime = stopWatch.getTotalTimeMillis();
        System.out.println();
        logger.info("["
                + joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "][" + joinPoint.getSignature().getName()
                + "]-method took [" + exeTime + "ms]");
        System.out.println();
    }
}
