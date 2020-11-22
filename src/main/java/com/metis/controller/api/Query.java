package com.metis.controller.api;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
    LinkedList<UserDO> queryByUserName(String name);

    /**
     * 查2
     * @return
     */
    @ResponseBody List<UserDO> queryAllUser();

}
