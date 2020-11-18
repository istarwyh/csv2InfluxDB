package com.metis.controller.api;

import com.metis.config.JsonResult;
import com.metis.entity.UserDO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:CRUD
 * @author:wangyihui
 * @Date:2020-10-2318:18
 * @version:1.0.0
 */
public interface CRUD {
    /**
     * 增
     * @return
     */
    @ResponseBody List<UserDO> testInsert();

    /**
     * 删
     * @param id
     * @return
     */
    @ResponseBody String testDelete(Integer id);
    /**
     * 改--使用实体类来接收前端数据
     * @param form
     * @return
     */
    @ResponseBody String testUpdate( @RequestBody  JsonResult<String> form );

    /**
     * 查1
     * @return
     */
    @ResponseBody UserDO testQuery();

    /**
     * 查2
     * @return
     */
    @ResponseBody List<UserDO> queryUserList();

}
