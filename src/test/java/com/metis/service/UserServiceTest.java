package com.metis.service;

import com.metis.dao.UserDAO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 通过@SpringBootTest可以在这里也使用IoC容器中的对象
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserDAO UserDAO;
    @Test
    void insertService() {
        UserDAO.insertUserLackId("Snail", 22, 2333.0);
        UserDAO.insertUserLackId("Daisy", 19, 3222.0);
    }
}