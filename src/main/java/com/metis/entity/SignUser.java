package com.metis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @Description: SignUser
 * @Author: wx:istarwyh
 * @Date: 2021-05-28 22:00
 * @Version: ing
 */
@Getter
@Setter
@SuperBuilder
public class SignUser {
    private BaseUser baseUser;
    private String   nickname;
    private String   passwd;

    /**
     * mybatis在读取到resultMap中constructor时会反射调用这个构造方法进行SignUser对象的构造与数据绑定
     */
    public SignUser(Long id, String name, String nickname, String passwd) {
        //        Warn:这里如果写this.baseUser.setId(id),那么因为baseUser不是基础类型没有被分配内存(如new),所以理论上会NPE;
        //         但是NPE始终反射过程中抛出的,所以最后会抛出 java.lang.reflect.InvocationTargetException
        this.baseUser = new BaseUser(id, name);
        this.nickname = nickname;
        this.passwd = passwd;
    }
}
