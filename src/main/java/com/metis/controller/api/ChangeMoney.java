package com.metis.controller.api;

import com.metis.entity.UserDO;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 说明接口是用于多组合避免单继承
 * @author: wangyihui
 * @Date: 2020-10-2320:18
 * @version: 1.0.0
 */
public interface ChangeMoney {
    /**
     * 模拟一个更改数据库中金额的操作
     *
     * @return
     */
    @ResponseBody
    List<UserDO> testChangeMoney();
}
