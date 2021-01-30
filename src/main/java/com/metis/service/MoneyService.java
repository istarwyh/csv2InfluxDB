package com.metis.service;

import com.metis.entity.User;

/**
 * @Description: MoneyService
 * @author: YiHui
 * @Date: 2020-12-04 14:24
 * @version: 1.0.0
 */
public interface MoneyService {
    /**
     * 测试钱财转账中出现的错误
     */
    void changeMoney();

    /**
     * 客户存款
     * @param money 向用户账户存款
     */
    User store(double money);
}
