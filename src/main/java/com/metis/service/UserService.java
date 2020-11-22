package com.metis.service;

import com.metis.dao.UserDAO;
import com.metis.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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

    public void insertService(UserDO userDO) {
        if( userDO.getId() == null ){
            UserDAO.insertUserLackId(userDO.getName(), userDO.getAge(),userDO.getMoney() );
        }else{
            UserDAO.insertUser(userDO.getId(), userDO.getName(),userDO.getAge(),userDO.getMoney());
        }
    }

    public void deleteService(long id) {
        UserDAO.deleteUser(id);
    }

    public void updateService(UserDO userDO) {
        if( selectUserById( userDO.getId() ) == null ){
            insertService(userDO);
        }else{
            UserDAO.updateUser(userDO.getName(), userDO.getAge(), userDO.getMoney(), userDO.getId());
        }
    }

    public LinkedList<UserDO> selectUserByName(String name) {
        return UserDAO.findUserByName(name);
    }

    public UserDO selectUserById(Long id){
        return UserDAO.findUserById(id);
    }

    public List<UserDO> selectAllUser() {
        return UserDAO.findAllUser();
    }

    /**
     * 模拟事务。 @Transactional注解并加上rollbackFor属性,事务在遇到非RuntimeException时也回滚
     * 如果参数readOnly值为true，则与方法相关的事务将在方法结束时取消（回滚）。在这种情况下该方法根本无法更改数据库中的信息。
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
