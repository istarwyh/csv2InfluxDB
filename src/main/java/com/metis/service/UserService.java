package com.metis.service;

import com.metis.entity.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: UserService
 * @author: YiHui
 * @Date: 2020-12-04 13:51
 * @version: 1.0.0
 */
public interface UserService extends MoneyService {
    /**
     * 增
     */
    void insertUser(User user);

    /**
     * 删
     */
    void deleteUserById(long id);

    /**
     * 改
     */
    void updateUser(User user);

    /**
     * 查
     * @param name
     * @return
     */
    LinkedList<User> selectUserByName(String name);

    /**
     * 查
     * @param id
     * @return
     */
    User selectUserById(Long id);

    /**
     * 查
     * @return
     */
    List<User> selectAllUser();
}
