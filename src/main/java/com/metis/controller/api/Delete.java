package com.metis.controller.api;

import com.metis.config.JsonResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @Description: Delete
 * @Author: YiHui
 * @Date: 2020-11-21 22:28
 * @Version: ing
 */
public interface Delete<T> {
    /**
     * 可以自定义返回的Json格式
     * @param id
     * @return
     */
    @ResponseBody String deleteById(Long id);
    /**
     * 返回的 JsonResult<T>会被自动转成Json
     * @param mapParam
     * @return
     */
    @ResponseBody JsonResult<T> deleteByMultiId(@RequestParam T mapParam);
}
