package com.metis.controller;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
import com.metis.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
/**
 * 告诉Spring MVC不需要使用服务器端视图层(view)渲染对象,而应该直接返回一个ResponseBody
 * Spring Boot 中默认使用的 Json 解析技术框架是 jackson,来自于spring-boot-starter-json 依赖
 */
@ResponseBody // @RestController = @ResponseBody + @Controller
/**
 * http请求头中需要带上"/user".
 * Request默认情况下映射所有的HTTP操作：get/post/delete...
 */
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/insert")
    public List<UserDO> testInsert() {
        userService.insertService();
        return userService.selectAllUser();
    }

    @RequestMapping("/delete")
    public String testDelete() {
        userService.deleteService(3);
        return "OK";
    }

    /**
     *TODO:接收的应该是post请求，从前端拿数据然后更改后端数据吧！
     */
    @RequestMapping("/update")
    public String testUpdate() {
        userService.updateService();
        return "OK";
    }

    @RequestMapping("/query")
    public UserDO testQuery() {
        return userService.selectUserByName("Daisy");
    }

    @RequestMapping("/changeMoney")
    public List<UserDO> testChangeMoney() {
        userService.changeMoney();
        return userService.selectAllUser();
    }


    /**
     * 使用实体类来接收前端传过来的数据
     * TODO:???
     */
    @PostMapping("/form")
    public String getPostBody(JsonResult<String> form ){
        return form.toString();
    }

/*    @GetMapping("/getUserList")
    public String getUserList(Model model){

    }*/
}
