package com.metis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 告诉Spring递归地搜索metis包和其子包中直接或间接标记为@Component的类。
 * @author wangyihui
 */
@ComponentScan
/**
 * 基于classpath的内容执行合理的默认行为,例如
 * 1.嵌入版的Tomcat（tomcat-embed-core.jar）自动建立并进行合理的默认配置
 * 2.spring-webmvc.jar 中的Spring MVC DispatcherServlet自动配置和注册 –- 不需要web.xml
 */
@EnableAutoConfiguration
@SpringBootConfiguration //@SpringBootApplication = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
public class MetisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetisApplication.class, args);
    }

}