package com.metis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 规范：所有POJO类初始类型必须是包装类型，即这里不可以是int/double这些 TODO：为什么呢？
 * 数据库中存储的每个类别都必须具有一个注释@Entity和一个@Id标有注释的属性，该属性用作数据库表的主键
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserDO implements Serializable {

    /**
     * 使用JPA时，该id属性通常是数字（Long或Integer），但是使用字符串变得越来越普遍
     * 该注释@GeneratedValue(strategy = GenerationType.AUTO)使数据库有责任在id字段中创建值。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "money")
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
