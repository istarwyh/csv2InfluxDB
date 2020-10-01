package com.metis.controller;

import com.metis.config.JsonResult;
import com.metis.dao.UserMapper;
import com.metis.entity.UserDO;
import com.metis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyihui
 */
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
public class UserController {
    @Autowired // 运行期间会动态创建一个userService注入，但是规范更提倡使用下面被注释掉的构造函数
    private  UserService userService;
  /*  private UserController(UserService userService) {
        this.userService = userService;
    }*/

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/insert")
    public List<UserDO> testInsert() {
        userService.insertService();
        return userService.selectAllUser();
    }

    @RequestMapping("/delete")
    public String testDelete() {
        userService.deleteService(3);
        return new JsonResult().toString();
    }

    /**
     *TODO:接收的应该是post请求，从前端拿数据然后更改后端数据吧?
     */
    @RequestMapping("/update")
    public String testUpdate() {
        userService.updateService();
        return new JsonResult().toString();
    }

    @RequestMapping("/query1")
    public UserDO testQuery() {
        return userService.selectUserByName("Daisy");
    }

    @GetMapping("/query2")
    @ResponseBody //其实这里不用加
    public List<UserDO> queryUserList(){
        return userMapper.queryUserList();
    }

    @RequestMapping("/changeMoney")
    public List<UserDO> testChangeMoney() {
        userService.changeMoney();
        return userService.selectAllUser();
    }

    /**
     * TODO:使用实体类来接收前端传过来的数据???
     */
    @PostMapping("/form")
    public String getPostBody(JsonResult<String> form ){
        return form.toString();
    }

    @GetMapping("/getUserList")
    public String getUserList(Model model){
        UserDO user1 = new UserDO(1,"yihui",24,3323.0);
        UserDO user2 = new UserDO(1,"lijun",23,2333.0);
        List<UserDO> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
    //  TODO:对象放到model中,但是model谁给的?model的参数又怎么传到thymeleaf页面的?Model支持的多种构造方式默认的应该怎么用?
        model.addAttribute( userList );
        //      指按MVC view的方式解析list,即找templates底下的list.html
        return "userList";
    }
}
