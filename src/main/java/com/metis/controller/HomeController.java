package com.metis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: homeController
 * @Author: YiHui
 * @Date: 2020-11-14 19:55
 * @Version: ing
 */
@Controller
public class HomeController {

    private String message;
    /**
     * 这里需要返回view,所以不要加@ResponseBody
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message",message);
        return "index";
    }

    /**
     * 接收表单数据之后必须：在处理POST的信息之后，作为响应将Redirect请求(302)返回给用户,之后浏览器会对重定向请求随附的地址发出新GET
         *  这样的web 开发设计模式因为最后返回的是GET得到的网页,所以不怕刷新重复提交form,也允许收藏
     */
    @PostMapping("/")
    public String postHome(@RequestParam String content) {
        this.message = content;
        return "redirect:/";
    }
}
