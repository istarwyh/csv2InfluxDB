package com.metis.dao;

import com.metis.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 与UserDO能实现一样的效果，只是这里在UserMapper.xml中映射
 */
@Mapper
public interface UserMapper {
    /**
     * 查询User在数据库中的那一行行
     * @return 所有User在数据库的信息
     */
    public List<UserDO> queryUserList();
}
