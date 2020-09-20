package com.metis.dao;

import com.metis.entity.UserDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 这里是将sql语句写在Java代码中
 * 还有一种是写在xml中
 */
@Mapper
/**
 * 2.0以上,@Repository其将数据访问(DO)层的类标识为Spring Bean
 * 并将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型
 */
@Repository
public interface UserDAO {
    /**
     * 增
     * @param name
     * @param age
     * @param money
     */
    @Insert("Insert into user(name,age,money) values(#{name},#{age},#{money})")
    void insertUser(@Param("name") String name, @Param("age") Integer age, @Param("money") Double money);
    /**
     * 删
     * @param id
     */
    @Delete("Delete from user where id=#{id}")
    void deleteUser(@Param("id") int id);

    /**
     * 改
     * @param name
     * @param age
     * @param money
     * @param id
     */
    @Update("Update user set name=#{name},age=#{age},money=#{money} where id=#{id}")
    void updateUser(@Param("name") String name, @Param("age") Integer age, @Param("money") Double money, @Param("id") int id);

    /**
     * 查
     * @param name
     * @return
     */
    @Select("Select * from user where name = #{name}")
    UserDO findUserByName(@Param("name") String name);

    /**
     * 查
     * @return
     */
    @Select("select * from user")
    List<UserDO> findAllUser();
}
