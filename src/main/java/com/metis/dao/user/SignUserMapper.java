package com.metis.dao.user;

import com.metis.entity.SignUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description: SignUserMapper
 * @Author: wx:istarwyh
 * @Date: 2021-05-28 22:12
 * @Version: ing
 */
@Mapper
public interface SignUserMapper {
    /**
     * 判断是否有(Name)的唯一用户
     * 
     * @param name 唯一真名
     * @return SignUser集合
     */
    SignUser readUserByName(String name);

    @Select("select id,name,nickname,passwd from user where ${column} = #{value}")
    SignUser findByColumn(@Param("column") String column, @Param("value") String value);
}
