package com.metis.dao.user;

import com.metis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 与UserDAO能实现一样的效果，只是这里在UserMapper.xml中映射 MyBatis 会根据接口和对应的 xml 文件创建接口的实现类
 * @implnote 可能有些会用DAO再封装一层Mapper来统一用DAO
 * 
 * @author MBin_王艺辉istarwyh
 */
@Mapper
public interface UserMapper {
    /**
     * 查询User在数据库中的那一行行
     * @return 所有User在数据库的信息
     */
    List<User> queryUserList();

}
