package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 这个实体类不与数据库表结构对应，而是作为单一实体
 * TODO:为什么@Autowired不能用于静态域与方法
 */
@Repository
public class InfluxClient1DO {
    /**
     *     如果要批量的得到配置信息应该怎么办呢?-->定义配置类并注入IOC容器,用时再导入
     */
    public static String host;

    public static  String database;

    public static  String retentionPolicy;

    public static  String username;

    public static  String password;

    @Value(value = "${spring.influx.url}")
    public  void setHost(String host) {
        InfluxClient1DO.host = host;
    }
    @Value("${spring.influx.database}")
    public  void setDatabase(String database) {
        InfluxClient1DO.database = database;
    }
    @Value("${spring.influx.retentionPolicy}")
    public  void setRetentionPolicy(String retentionPolicy) {
        InfluxClient1DO.retentionPolicy = retentionPolicy;
    }
    @Value(value = "${spring.influx.user}")
    public  void setUsername(String username) {
        InfluxClient1DO.username = username;
    }
    @Value(value = "${spring.influx.password}")
    public  void setPassword(String password) {
        InfluxClient1DO.password = password;
    }
}
