package com.metis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: BaseUser
 * @Author: wx:istarwyh
 * @Date: 2021-05-28 22:33
 * @Version: ing
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseUser {
    private Long   id;
    private String name;

    @Override
    public String toString() {
        return "BaseUser{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
