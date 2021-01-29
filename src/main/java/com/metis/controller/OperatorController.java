package com.metis.controller;

import com.metis.config.JsonResult;
import com.metis.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@Controller
@RequestMapping("/operator")
public class OperatorController {

    @GetMapping("/404") // @RequestMapping(method=GET)的简写
    @ResponseBody public String notFound(){ return new JsonResult<>(404,"来到了没有信息的荒原").toString();}

    /**
     *     虽然给的是index,但是会自动找/templates/下的500页面,如果没有就使用默认的404页面(包含错误信息)覆盖
     */
    @GetMapping("500")
    public String serverError(){
        // 模拟服务器运行错误
        int i = 1 / 0;
        return "index";
    }

    private static final String TEMPLATE;
    static {
        /*
         * %s 占位符,配合String.format()使用
         */
        TEMPLATE = "Hello %s!";
    }
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/greeting")
    @ResponseBody public JsonResult<?> greeting(@RequestParam( value = "name",required = false,defaultValue = " World") String name){
        // 当被转成int后又会被boxing成Integer,这时候对于JsonResult中定义的方法 方法签名才唯一
        return new JsonResult<>((int) counter.incrementAndGet(),String.format(TEMPLATE,name));
    }

    /**
     * Model可以认为是一个全局的对象,将Model对象作为参数添加到请求处理程序方法时，Spring允许它在Thymeleaf页面也被访问
     */
    @GetMapping("/getUserList")
    public String getUserList(Model model){
        User user1 = new User.Builder((long)1).name("yihui").age(24).money(2233.0).build();
        User user2 = new User.Builder((long)2).name("lijun").age(23).money(6666.0).build();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        //  TODO:对象放到model中,但是model谁给的?model的参数又怎么传到thymeleaf页面的?
        model.addAttribute( "u", userList);
        //      指按MVC view的方式解析list,即找templates底下的userList.html
        return "userList";
    }

}
