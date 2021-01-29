package com.metis.service;

import com.metis.dao.user.UserDAO;
import com.metis.dto.ContextDTO;
import com.metis.entity.User;
import com.metis.service.impl.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 1.就当前的项目来说,
 * 换成@Component,@Configuration其实对于将UserService作为一个类放到IOC容器中被其管理都是可行的
 * 2. Impl意指接口的实际实现者，而这个本身才应该称为服务，而不是一个方法
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO UserDAO;

    @Override
    public void insertUser(User user) {
        if( user.getId() == null ){
            UserDAO.insertUserLackId(user.getName(), user.getAge(), user.getMoney() );
        }else{
            UserDAO.insertUser(user.getId(), user.getName(), user.getAge(), user.getMoney());
        }
    }

    @Override
    public void deleteUserById(long id) {
        UserDAO.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        if( selectUserById( user.getId() ) == null ){
            insertUser(user);
        }else{
            UserDAO.updateUser(user.getName(), user.getAge(), user.getMoney(), user.getId());
        }
    }

    @Override
    public LinkedList<User> selectUserByName(String name) {
        return UserDAO.findUserByName(name);
    }

    @Override
    public User selectUserById(Long id){
        return UserDAO.findUserById(id);
    }

    @Override
    public List<User> selectAllUser() {
        return UserDAO.findAllUser();
    }

    /**
     * 模拟事务。 @Transactional注解并加上rollbackFor属性,事务在遇到非RuntimeException时也回滚
     * 如果参数readOnly值为true，则与方法相关的事务将在方法结束时取消（回滚）。在这种情况下该方法根本无法更改数据库中的信息。
     * 如果转账中途出了意外 Snail 和 Daisy 的钱都不会改变
     *      传入Exception.class -- 即其Class的对象，包含其元数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeMoney() {
        UserDAO.updateUser("Snail", 22, 2000.0, 3);
        // 模拟转账过程中可能遇到的意外状况
        int error = 1 / 0;
        UserDAO.updateUser("Daisy", 19, 4000.0, 4);
    }

    @Override
    public User store(double money) {
        ContextDTO context = new ContextDTO();

        double account = context.getUser().getMoney();
//        account += money其实也是个语法糖,实际是下面两步
        double newAccount = account + money;
        account = newAccount;
        context.getUser().setMoney(account);

        return context.getUser();
    }

}
