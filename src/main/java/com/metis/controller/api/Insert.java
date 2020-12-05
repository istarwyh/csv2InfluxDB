package com.metis.controller.api;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: insert
 * @Author: YiHui
 * @Date: 2020-11-21 22:19
 * @Version: ing
 */
public interface Insert<T> {
    /**
     * 增
     * @param t
     * @return
     */
    @ResponseBody JsonResult<List<T>> userInsert(@RequestBody T t);
}
