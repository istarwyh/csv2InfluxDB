package com.metis.controller;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        /**
         * %s 占位符,配合String.format()使用
         */
        TEMPLATE = "Hello,%s!";
    }
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/greeting")
    @ResponseBody public JsonResult<?> greeting(@RequestParam( value = "name",required = false,defaultValue = " World") String name){
        // 当被转成int后又会被boxing成Integer,这时候对于JsonResult中定义的方法 方法签名才唯一

        return new JsonResult<>((int) counter.incrementAndGet(),String.format(TEMPLATE,name));
    }

    /**
     * 如果有多个参数，则用分隔&,即.../mapReceiveParam?k1=v1&k2=v2&k3=v3
     * Get方法传参有长度限制,8000byte左右
     */
    @GetMapping("/mapReceiveParam")
    @ResponseBody public String mapReceiveParam(@RequestParam Map<String, String> mapParam) {
        return mapParam.keySet().toString() + "="+mapParam.values();
    }

    /**
     * Model可以认为是一个全局的对象,将Model对象作为参数添加到请求处理程序方法时，Spring允许它在Thymeleaf页面也被访问
     */
    @GetMapping("/getUserList")
    public String getUserList(Model model){
        UserDO user1 = new UserDO(1,"yihui",24,3323.0);
        UserDO user2 = new UserDO(1,"lijun",23,2333.0);
        List<UserDO> userDOList = new ArrayList<>();
        userDOList.add(user1);
        userDOList.add(user2);
        //  TODO:对象放到model中,但是model谁给的?model的参数又怎么传到thymeleaf页面的?
        model.addAttribute( "u",userDOList );
        //      指按MVC view的方式解析list,即找templates底下的userList.html
        return "userList";
    }

}
