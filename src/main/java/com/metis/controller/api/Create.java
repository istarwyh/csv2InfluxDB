package com.metis.controller.api;

import com.metis.config.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: insert
 * @Author: YiHui
 * @Date: 2020-11-21 22:19
 * @Version: ing
 */
public interface Create<T> {
    /**
     * 增
     * @param t
     * @return
     */
    @ResponseBody JsonResult<?> insert(@RequestBody T t);

}
