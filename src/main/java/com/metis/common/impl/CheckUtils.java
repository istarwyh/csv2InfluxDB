package com.metis.common.impl;

import cn.hutool.core.util.ObjectUtil;
import com.metis.config.exception.ExceptionMsgEnum;
import com.metis.dto.sign.SignUserDTO;
import com.metis.common.ICheck;

/**
 * @Description: CheckUtils
 * @Author: wx:istarwyh
 * @Date: 2021-05-29 21:05
 * @Version: ing
 */
public class CheckUtils implements ICheck {
    private CheckUtils() {
    }

    public static boolean checkLoginParam(SignUserDTO signUserDTO) {
        return Holder.INSTANCE.checkLoginParamIn(signUserDTO);
    }

    @Override
    public boolean checkLoginParamIn(SignUserDTO signUserDTO) {
        ExceptionMsgEnum.NULL_VALUE.assertNotNull(signUserDTO);
        return ObjectUtil.isNotNull(signUserDTO.getUsername()) && ObjectUtil.isNotNull(signUserDTO.getPassword());
    }

    /**
     * 嵌套内部类唯一持有instance的引用,外界也无法访问Holder
     */
    private static class Holder {
        private static final CheckUtils INSTANCE = new CheckUtils();
    }
}
