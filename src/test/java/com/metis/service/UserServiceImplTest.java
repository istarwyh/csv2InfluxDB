package com.metis.service;

import javax.annotation.Resource;
import java.util.List;
import com.metis.entity.User;
import com.metis.BaseTest;
import com.metis.dao.user.UserDAO;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class UserServiceImplTest extends BaseTest{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Resource
    private UserDAO userDAO;

    @Test
    void insertService() {
        userDAO.insertUserLackId("Snail", 22, 2333.0);
        userDAO.insertUserLackId("Daisy", 19, 3222.0);
    }

    @Test
    void test_findAllUser(){
        List<User> users =  userDAO.findAllUser();
        //  logback里面配置的日志级别是"INFO"，所以这里并不会在控制台输出
        logger.debug("all the users:\n{}",users);
    }
}