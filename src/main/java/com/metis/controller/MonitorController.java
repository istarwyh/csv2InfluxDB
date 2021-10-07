package com.metis.controller;

import com.metis.dto.ResponseDTO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: MonitorController
 * @Author: YiHui
 * @Date: 2021-01-29 16:59
 * @Version: ing
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    //    @RequiresPermissions("metis:monitor:view")
    @GetMapping()
    public String monitor() {
        return "monitor/monitor";
    }

    @GetMapping("/404") // @RequestMapping(method=GET)的简写
    @ResponseBody
    public String notFound() {
        return new ResponseDTO<>(404, "来到了没有信息的荒原").toString();
    }

}
