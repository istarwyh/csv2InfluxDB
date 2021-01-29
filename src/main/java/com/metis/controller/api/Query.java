package com.metis.controller.api;

import com.metis.entity.User;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: CRUD
 * @author: wangyihui
 * @Date: 2020-10-2318:18
 * @version: 1.0.0
 */
public interface Query {

    /**
     * 查1
     * @return
     * @param name
     */
    @ResponseBody
    LinkedList<User> queryByUserName(String name);

    /**
     * 查2
     * @return
     */
    @ResponseBody List<User> queryAllUser();

}
