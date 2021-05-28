package com.metis.controller.api;

import com.metis.config.ResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    ResponseDTO<?> insert(@RequestBody T t);

}
