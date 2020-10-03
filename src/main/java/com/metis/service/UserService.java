package com.metis.service;

import com.metis.dao.UserDAO;
import com.metis.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 就当前的项目来说,
 * 换成@Component,@Configuration其实对于将UserService作为一个类放到IOC容器中被其管理都是可行的
 */
@Service
public class UserService {
    @Autowired
    private UserDAO UserDAO;

//    public UserService(UserDAO UserDAO) {
//        this.UserDAO = UserDAO;
//    }

    public void insertService() {
//        因为id是AUTO_INCREMENT的(mysql中的设计),所以直接插入其他信息也就可以了
        UserDAO.insertUser("Snail", 22, 3000.0);
        UserDAO.insertUser("Daisy", 19, 3000.0);
    }

    public void deleteService(int id) {
        UserDAO.deleteUser(id);
    }

    public void updateService() {
        UserDAO.updateUser("YiHui", 23, 5000.0, 1);
    }

    public UserDO selectUserByName(String name) {
        return UserDAO.findUserByName(name);
    }

    public List<UserDO> selectAllUser() {
        return UserDAO.findAllUser();
    }

    /**
     * 模拟事务。 @Transactional注解并加上rollbackFor属性,事务在遇到非RuntimeException时也回滚
     * 如果转账中途出了意外 Snail 和 Daisy 的钱都不会改变
     *      传入Exception.class -- 即其Class的对象，包含其元数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeMoney() {
        UserDAO.updateUser("Snail", 22, 2000.0, 3);
        // 模拟转账过程中可能遇到的意外状况
        int temp = 1 / 0;
        UserDAO.updateUser("Daisy", 19, 4000.0, 4);
    }

}
