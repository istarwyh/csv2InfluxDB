package com.metis.controller.api;

import com.metis.config.JsonResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: Update
 * @Author: YiHui
 * @Date: 2020-11-21 22:31
 * @Version: ing
 */
public interface Update<T> {

    /**
     *
     * @param t
     * @return
     */
    @ResponseBody
    JsonResult<T> update(@RequestBody T t);
}
