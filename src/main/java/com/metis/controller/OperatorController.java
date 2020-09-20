package com.metis.controller;

import com.metis.config.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operator")
public class OperatorController {

    @GetMapping("/404")
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
}
