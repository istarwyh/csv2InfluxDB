package com.metis.common;

import com.metis.dto.sign.SignUserDTO;

/**
 * @Description: ICheck
 * @Author: wx:istarwyh
 * @Date: 2021-05-29 21:02
 * @Version: ing
 */
public interface ICheck {
    /**
     * 检查是否正常登录流程
     * 
     * @param signUserDTO 登录DTO对象
     * @return
     */
    boolean checkLoginParamIn(SignUserDTO signUserDTO);
}
