package com.example.metis.service;

import com.example.metis.model.KeyValueModel;
import com.example.metis.model.LineProtocolModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//TODO:Test方法在启动上是不是就相当于main方法?
class UtilsTest {
//    实例变量默认被全部对象及对象的方法共享,即变量作用域在类中--只不过是给过程式编程中的全局变量找了在OO中的意义
    private String folderPath = "./repository/";
    private String fileName = "test.csv";

    @Test
    void transfer() {
//        TODO:加了构造函数后,为什么不能执行呢?
//        new UtilsTest(contentPath,"test.csv").readCSV();
    }

    @Test
    void readCSV() {
        List<List<String>> userRoleLists = Utils.readCSV(folderPath +fileName);
        assertNotNull(userRoleLists);
        List<KeyValueModel> modelList = Utils.transfer(userRoleLists);
//        采用直接打印检查输出某些时候比构造测试用例的输出再assert()简单
//        但是这样不能自动化
        System.out.println(modelList);
    }

    @Test
    void CSVToList() {
        String testName = "test";
        List<LineProtocolModel> lineprotocolList = Utils.CSVToList(folderPath +fileName,testName);
        System.out.println(lineprotocolList.get(0));
        System.out.println(lineprotocolList.get(1));

    }

    @Test
    void copyToLocal() {
        String testName = "test";
        List<LineProtocolModel> lineprotocolList = Utils.CSVToList(folderPath +fileName,testName);
        String newFolderPath = "H:\\桌面\\test\\";
        Utils.copyToLocal(newFolderPath,lineprotocolList);
    }
}