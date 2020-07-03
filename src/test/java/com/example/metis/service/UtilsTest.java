package com.example.metis.service;

import com.example.metis.model.Model_KY;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//TODO:Test方法在启动上是不是就相当于main方法?
class UtilsTest {
//    实例变量默认被全部对象及对象的方法共享,即变量作用域在类中--只不过是给过程式编程中的全局变量找了在OO中的意义
    String filePath = "./repository/";
    String fileName = "test.csv";

    @Test
    void transfer() {
//        TODO:加了构造函数后,为什么不能执行呢?
//        new UtilsTest(filePath,"test.csv").readCSV();
    }

    @Test
    void readCSV() {
        List<List<String>> userRoleLists = Utils.readCSV(filePath+fileName);
        assertNotNull(userRoleLists);
        List<Model_KY> modelList = Utils.transfer(userRoleLists);
        System.out.println(modelList);
    }
}