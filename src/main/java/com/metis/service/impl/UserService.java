package com.metis.service.impl;

import com.metis.entity.UserDO;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: UserService
 * @author: YiHui
 * @Date: 2020-12-04 13:51
 * @version: 1.0.0
 */
public interface UserService extends MoneyService{
    /**
     * 增
     */
    void insertUser(UserDO userDO);

    /**
     * 删
     */
    void deleteUserById(long id);

    /**
     * 改
     */
    void updateUser(UserDO userDO);

    /**
     * 查
     * @param name
     * @return
     */
    LinkedList<UserDO> selectUserByName(String name);

    /**
     * 查
     * @param id
     * @return
     */
    UserDO selectUserById(Long id);

    /**
     * 查
     * @return
     */
    List<UserDO> selectAllUser();
}
