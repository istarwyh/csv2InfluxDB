package com.metis.dao.user;

import com.metis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 与UserDO能实现一样的效果，只是这里在UserMapper.xml中映射
 * @author MBin_王艺辉istarwyh
 */
@Mapper
public interface UserMapper {
    /**
     * 查询User在数据库中的那一行行
     * @return 所有User在数据库的信息
     */
    public List<User> queryUserList();
}