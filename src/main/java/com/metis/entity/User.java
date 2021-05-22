package com.metis.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 规范：所有POJO类属性类型必须是包装类型，即这里不可以是int/double这些:
 *      1. 使用Integer而不是int,可以充分利用虚拟机提供的缓存(默认-128-127)
 *      2. 当POJO类对应数据库表,使用int将不能区分表中字段为空与为0的情况,因为为空的字段返回后对应到int的属性是0,对应到Integer是null,后者可以表示空字段
 *      3. POJO类被序列化传输时,如果调用方得到null则可以表示调用异常,使用int则无此功能
 * 数据库中存储的每个类别都必须具有一个注释@Entity和一个@Id标有注释的属性，该属性用作数据库表的主键->JPA面向对象开发时
 * Mybatis可能更多像一个driver(驱动),以sql为中心,将sql发送给Database执行完后再将数据绑定到业务对象上
 * @author MBin_王艺辉istarwyh
 */
@Getter
@Setter
@NoArgsConstructor
//@Builder
@Entity // @Entity表明这是一个映射到数据库的实体类
public class User implements Serializable {

    /**
     * 使用JPA时，该id属性通常是数字（Long或Integer），但是使用字符串变得越来越普遍
     * 该注释@GeneratedValue(strategy = GenerationType.AUTO)使数据库有责任在id字段中创建值。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "money")
    private Double money;

    private User(Builder builder){
        this.id = builder.id;
        this.age = builder.age;
        this.name = builder.name;
        this.money = builder.money;
    }

    public static class Builder{
        private final Long id;
        private String name;
        private Integer age;
        private Double money;
        public Builder(Long id){
            this.id = id;
        }
        public Builder name(String val){
            name = val;
            return this;
        }
        public Builder age(Integer val){
            age = val;
            return this;
        }
        public Builder money(Double val){
            this.money = val;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

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
