package com.metis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDO {
    /**
     * 规范：所有POJO类初始类型必须是包装类型，即这里不可以是int/double这些
     * TODO：为什么呢？
     */
    private Integer id;
    private String name;
    private Integer age;
    private Double money;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                '}';
    }
}
