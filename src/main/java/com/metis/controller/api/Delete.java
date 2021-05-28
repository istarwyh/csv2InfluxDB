package com.metis.controller.api;

import com.metis.dto.ResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 删除 String[] 类型的ids的数据
     * @param ids
     * @return
     */
    @ResponseBody
    ResponseDTO<?> deleteByIds(@RequestBody String[] ids);
}
