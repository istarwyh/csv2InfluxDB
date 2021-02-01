package com.metis.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.metis.annotation.KthLog;
import com.metis.config.business.BusinessType;
import com.metis.config.JsonResult;
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
public class MonitorController implements BaseController<Monitor>
{

    @Resource
    private IMonitorService monitorService;

//    @RequiresPermissions("metis:monitor:view")
    @GetMapping()
    public String monitor()
    {
        return "monitor/monitor";
    }

    /**
     * 新增保存info of monitor
     */
    @RequiresPermissions("metis:monitor:add")
    @KthLog(title = "info of monitor", businessType = BusinessType.INSERT)
    @PostMapping("/insert")
    @ResponseBody
    @Override public JsonResult<?> insert(Monitor monitor) {
        int data = monitorService.insertMonitor(monitor);
        return new JsonResult<>(data);
    }

    @Override
    @KthLog(title = "info of monitor", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public String deleteById(Long id) {
        return new JsonResult<>(monitorService.deleteMonitorById(id)).toString();
    }

    @GetMapping("/404") // @RequestMapping(method=GET)的简写
    @ResponseBody public String notFound(){ return new JsonResult<>(404,"来到了没有信息的荒原").toString();}
    /**
     *
     * @param ids
     * @return
     */
    @Override
    @RequiresPermissions("metis:monitor:remove")
    @KthLog(title = "info of monitor", businessType = BusinessType.REMOVE)
    @PostMapping( "/remove")
    public JsonResult<?> deleteByIds(@RequestBody String[] ids) {
        return new JsonResult<>(monitorService.deleteMonitorByIds(ids));
    }

    /**
     * 修改info of monitor
     */
    @GetMapping("/select/{traceId}")
    @ResponseBody
    @KthLog(title = "info of monitor", businessType = BusinessType.SELECT)
    public Monitor selectById(@PathVariable("traceId") String traceId){
        return monitorService.selectMonitorById(Long.parseLong(traceId));
    }

    @Override public List<Monitor> queryAll(){
        return null;
    }

    @Override
    public JsonResult<Monitor> update(Monitor monitor) {
        return null;
    }
}
