package com.metis.dao.user;

import com.metis.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * 这里是将sql语句写在Java代码中
 * 还有一种是写在xml中:复杂的 SQL与代码分开
 */
@Mapper
/**
 * 2.0以上,@Repository其将数据访问(DAO)层的类标识为Spring Bean
 * 并将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型
 */
@Repository
public interface UserDAO {
    /**
     * 依靠自增AUTO_INCREMENT的(mysql中的设计)id新建一个user
     * @param name
     * @param age
     * @param money
     */
    @Insert("Insert into user(name,age,money) values(#{name},#{age},#{money})")
    void insertUserLackId(@Param("name") String name, @Param("age") Integer age, @Param("money") Double money);

    /**
     * 新建一个user
     * @param name
     * @param age
     * @param money
     */
    @Insert("Insert into user(id,name,age,money) values(#{id},#{name},#{age},#{money})")
    void insertUser(@Param("id") Long id,@Param("name") String name, @Param("age") Integer age, @Param("money") Double money);

    /**
     * 删
     * @param id
     */
    @Delete("Delete from user where id=#{id}")
    void deleteUser(@Param("id") long id);

    /**
     * 改
     * @param name
     * @param age
     * @param money
     * @param id
     */
    @Update("Update user set name=#{name},age=#{age},money=#{money} where id=#{id}")
    void updateUser(@Param("name") String name, @Param("age") Integer age, @Param("money") Double money, @Param("id") long id);

    /**
     * 通过用户名查
     * 这个有坑在于name是不能作为唯一键的,返回的也自然可能是UserDO,可能是List<UserDO>
     * @param name
     * @return
     */
    @Select("Select * from user where name = #{name}")
    LinkedList<User> findUserByName(@Param("name") String name);

    /**
     * 通过用户名查
     * @param id
     * @return
     */
    @Select("Select * from user where id = #{id}")
    User findUserById(@Param("id") Long id);

    /**
     * 查询所有用户信息
     * @return
     */
    @Select("select * from user order by id desc")
    List<User> findAllUser();
}
