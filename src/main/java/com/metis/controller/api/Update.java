package com.metis.controller.api;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
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
     * @return 必须要返回JsonResult<UserDO>吗?而不能是一个泛型?
     */
    @ResponseBody
    JsonResult<T> update(@RequestBody T t);
}
