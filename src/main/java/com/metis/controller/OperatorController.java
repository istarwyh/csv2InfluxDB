package com.metis.controller;

import com.metis.config.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 这里需要返回view,所以不要加@ResponseBody了
 */
@Controller
@RequestMapping("/operator")
public class OperatorController {

    @GetMapping("/404") // @RequestMapping(method=GET)的简写
    public String notFound(){ return  new JsonResult<>(404,"来到了没有信息的荒原").toString();}
    /**
     *     虽然给的是index,但是会自动找/templates/下的404页面,如果没有就使用默认的404页面(包含错误信息)覆盖
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
    public JsonResult greeting(@RequestParam( value = "name",required = false,defaultValue = " World") String name){
        // 当被转成int后又会被boxing成Integer,这时候对于JsonResult中定义的方法 方法签名才唯一
        return new JsonResult((int) counter.incrementAndGet(),String.format(TEMPLATE,name));
    }

}
