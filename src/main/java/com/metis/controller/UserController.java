package com.metis.controller;

import com.metis.annotation.KthLog;
import com.metis.config.JsonResult;
import com.metis.controller.api.*;
import com.metis.dao.UserMapper;
import com.metis.entity.UserDO;
import com.metis.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
/**
 * 告诉Spring MVC不需要使用服务器端视图层(view)渲染对象,而应该直接返回一个ResponseBody(String类型)
 * Spring Boot 中默认使用的 Json 解析技术框架是 jackson,来自于spring-boot-starter-json 依赖
 */
@ResponseBody // @RestController = @ResponseBody + @Controller
/**
 * http请求头中需要带上"/user".
 * Request默认情况下映射所有的HTTP操作：get/post/delete...
 */
@RequestMapping("/user")
public class UserController<T> implements  Insert<UserDO> , Delete<Map<String, String>>, Update<UserDO>,Query, ChangeMoney {
    /**
     * Spring 常用的依赖注入方法:
     * 1. 构造器注入：利用构造方法的参数注入依赖
     * 2. Setter注入：调用Setter的方法注入依赖
     * 3. 字段注入：在字段上使用@Autowired/Resource注解
     * 运行期间会动态创建一个UserService(以接口方式提供)注入，但是规范更提倡使用下面被注释掉的构造函数
     */
    @Autowired
    private UserService userService;
//    private UserController(UserService userService) {
//        this.userService = userService;
//    }

    /**
     * Spring提供的@Autowired,即它是特定IoC提供的特定注解，这就导致了应用与框架的强绑定，一旦换用了其他IOC框架是不能够支持注入的。
     * 而@Resource是JSR-250提供的，它是Java标准，我们使用的IoC容器应当去兼容它，这样即使更换容器，也可以正常工作。
     * 因此对于@Autowired是不建议,对于@Resource却无反应
     */
    @Resource
    private UserMapper userMapper;

    /**
     * 该属性consumes指示该方法侦听的地址接受哪种数据: JSON格式的数据"application/json"(http请求首部)
     * produces则指示有关它产生的数据的信息(http响应首部)
     */
    @Override
    @PostMapping(path= "/insert", consumes="application/json", produces="application/json")
    public JsonResult<List<UserDO>> userInsert(@RequestBody UserDO t) {
        if( t == null ) {
            return new JsonResult<>(1,"插入的对象为空");
        }
//        boostrap类加载器或者restartClassLoader默认把传进来的泛型参数当成LinkedHashMap
//        LinkedHashMap<String, String> map = (LinkedHashMap)t;
//        UserDO userDO = new UserDO();
//        for(Map.Entry<String, String> m : map.entrySet() ){
//            switch ( m.getKey() ){
//                case "id": userDO.setId( Long.parseLong( m.getValue() ));break;
//                case "age": userDO.setAge( Integer.parseInt( m.getValue()));break;
//                case "name": userDO.setName( m.getValue());break;
//                case "money": userDO.setMoney(Double.valueOf(m.getValue()));break;
//                default: {
//                    ArrayList<UserDO> failObjList = new ArrayList<>();
//                    failObjList.add(userDO);
//                    return new JsonResult<>(failObjList, "插入对象属性名不完全匹配");
//                }
//            }
//            System.out.println(m.getKey()+"  "+m.getValue());
//        }
        userService.insertUser( t );
        List<UserDO> allUser = userService.selectAllUser();
        System.out.println( allUser );
        return new JsonResult<>(allUser);
    }

    /**
     * 直接在http的url中传参有长度限制,8000byte左右,ie2000byte左右
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userService.deleteUserById( id );
        return new JsonResult<T>(0, String.valueOf(id) ).toString();
    }

    /**
     * @param mapParam 1. 如果有多个参数，则用分隔&,即.../delete?k1=v1&k2=v2&k3=v3
     *                 2. 这里不能使用IdentityHashMap来实现接收"id=1&id=2"这样key相同的格式的目的,
     *                 因为如果不是使用对象接收,则默认是LinkedHashMap接收,
     *                 而IdentityHashMap并不是LinkedHashMap的父类,就会出现类型不匹配的错误
     *                 3. 这里不要用Map<String,Long>这种格式接收,因为传过来的数据默认是LinkedHashMap<String,String>
     */
    @Override
    @DeleteMapping("/delete")
    public JsonResult<Map<String, String>> deleteByMultiId(@RequestParam Map<String, String> mapParam) {
        for( String id : mapParam.values() ) {
            userService.deleteUserById( Long.parseLong(id) );
        }
        return new JsonResult<>( mapParam );
    }

    @Override
    @PutMapping("/update")
    public JsonResult<UserDO> update(@RequestBody UserDO userDO) {
        if( userDO == null ){
            return new JsonResult<UserDO>(2,"参数不是UserDO类型");
        }
        userService.updateUser(userDO);
        if(userDO.getId() == null) {
            LinkedList<UserDO> matchedUser = userService.selectUserByName( userDO.getName());
            int len = matchedUser.size();
            userDO.setId( matchedUser.get(len-1).getId());
        }
        return new JsonResult<>(userDO);
    }

    /**
     * 这里不加@RequestParam也没有关系
     * @param name
     * @return
     */
    @Override
    @KthLog("这是想要输出的日志内容,这里输入后会被自定义的 KthLogger对象的value() 拿到")
    @GetMapping("/queryByUserName")
    public LinkedList<UserDO> queryByUserName(String name) {
        return userService.selectUserByName(name);
    }

    @Override
    @GetMapping("/queryAllUser")
    @KthLog("查询所有的用户名单")
    public List<UserDO> queryAllUser(){
        return userMapper.queryUserList();
    }

    @Override
    @RequestMapping("/changeMoney")
    public List<UserDO> testChangeMoney() {
        userService.changeMoney();
        return userService.selectAllUser();
    }
}
