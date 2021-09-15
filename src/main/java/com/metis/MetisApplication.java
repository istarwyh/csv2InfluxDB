package com.metis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 1. 告诉Spring递归地搜索metis包和其子包中直接或间接标记为@Component的类
 * 2. 将上述被特定注解的类通过反射实例化(Class->Object),以Map形式保存.Map的key为类名,value为类对象.
 * 3. 再一次遍历上述类获取其中所有字段,
 *      如果字段key上有@Autowired或@Resource->从Bean容器中拿到对应的对象value,也有可能先拿到的是接口,那么获取接口对应实现类再将其实例化取值
 * 4. 完成对象都放入IOC容器中
*/
@ComponentScan
/**
 * 基于classpath的内容执行合理的默认行为,例如
 * 1.嵌入版的Tomcat（tomcat-embed-core.jar）自动建立并进行合理的默认配置
 * 2.spring-webmvc.jar 中的Spring MVC DispatcherServlet自动配置和注册 –- 不需要web.xml
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
/**
 * 声明这是一个注解，@SpringBootConfiguration 封装了一些定义注解的注解(元注解)，如注解适用类型与生命周期等
 *     --> 用一个@SpringBootApplication也就可以了,因为 @SpringBootApplication =
 *     @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
 * @author MBin_王艺辉istarwyh
 */
@SpringBootConfiguration
public class MetisApplication {

    public static void main(String[] args) {
//        也可以在application.properties中配置
//        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MetisApplication.class, args);
    }

}