package com.metis.controller;

import com.metis.config.JsonResult;
import com.metis.config.business.BusinessErrorException;
import com.metis.config.business.BusinessMsgEnum;
import com.metis.entity.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author MBin_王艺辉istarwyh
 * 如果没有对应的映射,就返回默认的404页面/json(包含错误信息)
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {
    /**
     *  没有GlobalExceptionHandler全局拦截的时候, "i = 1 / 0" --> GET "/error", parameters={}
     *      org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#errorHtml:
     *     - 对于浏览器访问,会自动找/templates/下的500页面;如果没有,会报一个默认的whitelabel error view
     *     - 对于APP访问,会以json形式报全部的error detail
        设置了自适应的GlobalExceptionHandler:
           -
     */
    @GetMapping("500")
    public String serverError(){
        // 模拟服务器运行错误
        int i = 1 / 0;
        return "index";
    }

    /**
     * 模拟抛出 BusinessMsgEnum.UNEXPECTED_EXCEPTION 业务异常
     * @return
     */
    @GetMapping("/business")
    public JsonResult<?> testBusinessException() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new
                    BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
        return new JsonResult<>();
    }

    private static final String TEMPLATE;
    static {
        /*
         * %s 占位符,配合String.format()使用
         */
        TEMPLATE = "Hello %s!";
    }
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/greeting")// @RequestMapping(method=GET)的简写
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
