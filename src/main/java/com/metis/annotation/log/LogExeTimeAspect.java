package com.metis.annotation.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: LogExeTimeAspect
 * @Author: Essex Boy
 * @Date: 2021-02-04 19:49
 * @Version: ing
 */
@Aspect
@Component
public class LogExeTimeAspect {
    private final List<String> messages = new ArrayList<>();
    private final List<Throwable> exceptions = new ArrayList<>();
    /**
     * 环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的,只有proceed才能支持 aop:around 这种切面
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.metis.annotation.log.LogExecutionTime)")
    public Object handleLogging(ProceedingJoinPoint joinPoint) throws Throwable {

        Object proceed = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String startMessage = startMessage(joinPoint);
        debug(joinPoint, startMessage(joinPoint));
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable t) {
            error(joinPoint, "exception thrown calling " + startMessage, t);
        } finally {
            stopWatch.stop();
            debug(joinPoint, "method " + joinPoint.getSignature().toShortString() + " took "
                    + stopWatch.getTotalTimeMillis() + "ms");
        }

        if (proceed == null) {
            debug(joinPoint, joinPoint.getSignature().toShortString() + " returned void");
        } else {
            debug(joinPoint, joinPoint.getSignature().toShortString() + " returned " + proceed);
        }

        return proceed;
    }

    private String startMessage(JoinPoint joinPoint) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(joinPoint.getSignature().toShortString());
        stringBuilder.append("\n");
        Object[] params = joinPoint.getArgs();
        for (Object param : params) {
            stringBuilder.append("\t-> ").append(param);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private Logger getLogger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
    }

    private void debug(JoinPoint joinPoint, String message) {
        messages.add(message);
        getLogger(joinPoint).debug(message);
    }

    private void error(JoinPoint joinPoint, String message, Throwable t) {
        messages.add(message);
        exceptions.add(t);
        getLogger(joinPoint).error(message, t);
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }
}
