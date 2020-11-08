package com.metis.controller;

import com.metis.annotation.KthLog;
import com.metis.config.JsonResult;
import com.metis.controller.api.CRUD;
import com.metis.controller.api.ChangeMoney;
import com.metis.dao.UserMapper;
import com.metis.entity.UserDO;
import com.metis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
public class UserController implements CRUD, ChangeMoney {
    @Autowired // 运行期间会动态创建一个userService注入，但是规范更提倡使用下面被注释掉的构造函数
    private  UserService userService;
//    private UserController(UserService userService) {
//        this.userService = userService;
//    }

    @Autowired
    private UserMapper userMapper;

    @Override
    @RequestMapping("/insert")
    public List<UserDO> testInsert() {
        userService.insertService();
        return userService.selectAllUser();
    }


    @Override
    public String testUpdate(@RequestBody JsonResult<String> form) {
        userService.updateService();
        return new JsonResult().toString();
    }

    @Override
    @RequestMapping("/delete")
    public String testDelete(int id) {
        id =3;
        userService.deleteService(id );
        return new JsonResult().toString();
    }

    @Override
    @KthLog("这是想要输出的日志内容,这里输入后会被自定义的 KthLogger对象的value() 拿到")
    @RequestMapping("/query1")
    public UserDO testQuery() {
        return userService.selectUserByName("Daisy");
    }

    @Override
    @GetMapping("/query2")
    @ResponseBody //其实这里不用加
    public List<UserDO> queryUserList(){
        return userMapper.queryUserList();
    }

    @Override
    @RequestMapping("/changeMoney")
    public List<UserDO> testChangeMoney() {
        userService.changeMoney();
        return userService.selectAllUser();
    }

}
