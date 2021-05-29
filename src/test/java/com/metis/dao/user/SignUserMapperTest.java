package com.metis.dao.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

/**
 * 对 DAO 层的测试是为了两点： 1. 它执行了正确的 SQL 吗？ 2. 它返回了我们所需要的数据吗？
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SignUserMapperTest {
    @Autowired
    SignUserMapper signUserMapper;

    @Test
    @Rollback(value = true)
    void readUserByName() {
        Assertions.assertNotNull(signUserMapper.readUserByNameAndPasswd("yihui", "88888888"));
    }
}
