package com.metis.controller.api;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: CRUD
 * @author: wangyihui
 * @Date: 2020-10-2318:18
 * @version: 1.0.0
 */
public interface Read<T> {

    /**
     * 查2
     * @return
     */
    @ResponseBody List<T> queryAll();

}
