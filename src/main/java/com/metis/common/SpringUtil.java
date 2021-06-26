package com.metis.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Description: SpringUtil
 * @Author: mameng1998
 * @Date: 2021-02-05 21:45
 * @Version: ing
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public static <T> T getBean(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    public static <T> T getBean(String name, Class<T> cal) {
        return applicationContext.getBean(name, cal);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * get the proxy Object by the runtime class of this Object
     */
    public static <T> T getProxyObj(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    public static String getProperty(String key) {
        return applicationContext.getBean(Environment.class).getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
}
