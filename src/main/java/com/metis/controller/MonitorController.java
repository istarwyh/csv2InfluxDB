package com.metis.controller;

import java.util.List;

import javax.annotation.Resource;

import com.metis.dto.ResponseDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.metis.annotation.log.KthLog;
import com.metis.config.business.BusinessType;
import com.metis.controller.api.BaseController;
import com.metis.entity.Monitor;
import com.metis.service.IMonitorService;

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
