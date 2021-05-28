package com.metis.annotation.business;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.metis.entity.User;

/**
 * @Description: Modify1Aspect
 * @Author: YiHui
 * @Date: 2021-02-01 21:28
 * @Version: ing
 */
@Component
/**
 * Spring AOP 借助AspectJ的Pointcut表述语言的外衣推出了@Aspect
 * AspectJ支持的Pointcut表达式可用的标识符基本可以表示所有的Joinpoint,但是Spring AOP只支持方法级别的Joinpoint
 */
@Aspect
public class Modify1Aspect {
    /**
     * pointcut切点 用 @Pointcut 声明自定义的注解类(@Interface) KthLog 为切面
     * 使用@annotation的Pointcutbi表达式将会检查系统中所有对象的方法级别的Joinpoint
     */
    @Pointcut("@annotation(com.metis.annotation.business.Modify1)")
    private void pointcut() {
    }
    /**
     * @param pjp ProceedingJoinPoint是JoinPoint的子接口,该对象只用在@Around的切面方法中
     * @return 截取user对象，并修改其值。这里模拟前端从0开始计数，而后端从1开始计数的场景。在这种场景下，对后端返回的计数到前端时加1.
     * @throws Throwable
     */
    @Around(value = "pointcut()")
    public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal;
        try {
            retVal = pjp.proceed();
        } catch (Throwable throwable) {
            throw new Throwable("执行proceed()出错");
        }
        //并对返回值进行增强处理。这里假设User的id需要从 1 以开始计数，而前端是从 0 开始计数
        if (retVal != null) {
            if (retVal instanceof User) {
                final User user = (User) retVal;
                user.setId(user.getId() + 1);
                return user;
            } else if (retVal instanceof List) {
                final List<User> userList = (List<User>) retVal;
                for (User u : userList) {
                    u.setId(u.getId() + 1);
                }
                return userList;
            } else {
                throw new Exception("result不是UserDO和List<UserDO>的实例（is-a)");
            }
        } else {
            throw new NullPointerException("没有找到指定编号的user");
        }
    }
    //    似乎目标方法返回值必须定为retVal...那这个可以不用了
    //    @AfterReturning(pointcut = "pointcut()",returning = "retVal")
    //    public Object afterReturning(JoinPoint joinPoint, Object retVal) throws Exception{
    //     
    //    }
}
