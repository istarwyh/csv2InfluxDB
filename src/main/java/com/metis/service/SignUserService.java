package com.metis.service;

import com.metis.entity.SignUser;

/**
 * @Description: SignUserService
 * @Author: wx:istarwyh
 * @Date: 2021-06-26 18:01
 * @Version: ing
 */
public interface SignUserService {
    /**
     * 判断数据库中的是否有匹配的登录信息
     */
    boolean existMatchedUser(SignUser signUser);
}
