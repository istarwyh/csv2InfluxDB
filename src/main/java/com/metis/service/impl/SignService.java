package com.metis.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.metis.dao.user.SignUserMapper;
import com.metis.entity.SignUser;
import com.metis.service.SignUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description: SignService
 * @Author: wx:istarwyh
 * @Date: 2021-05-30 00:42
 * @Version: ing
 */
@Service
public class SignService implements SignUserService {
    @Resource
    SignUserMapper signUserMapper;

    @Override
    public boolean existMatchedUser(SignUser signUser) {
        return ObjectUtil.isNotNull(
                signUserMapper.readUserByNameAndPasswd(signUser.getBaseUser().getName(), signUser.getPasswd()));
    }
}
