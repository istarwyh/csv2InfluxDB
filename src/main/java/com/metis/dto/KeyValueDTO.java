package com.metis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author : zhaxinchi
 * @date : 2020/6/2
 * DTO中的实体核心在于其实例化不直接与数据库相关
 * 且往往作为数据库与业务层中间的数据结构存在
 */

@Getter
@Setter
@AllArgsConstructor
public class KeyValueDTO implements Serializable {
    String key;
    String value;

    @Override
    public String toString() {
        return "Model_KY{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
