package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 这个实体类不与数据库表结构对应，而是作为单一实体
 * 添加到IOC容器中，从而使得@ComponentScan可以扫描到
 */
@Repository
//@ConfigurationProperties(ignoreUnknownFields =false, prefix = "spring.influx") // 找到配置文件中属性的前缀,之后返回对应属性

public class InfluxClient2DO {
    public static String token;
    public static String cloudUrl;
    public static String bucket;
    public static String org;

    @Value(value="${spring.influx.token}")
    public  void setToken(String token) {
        InfluxClient2DO.token = token;
    }
    @Value(value="${spring.influx.cloudUrl}")
    public  void setCloudUrl(String cloudUrl) {
        InfluxClient2DO.cloudUrl = cloudUrl;
    }
    @Value(value="${spring.influx.bucket}")
    public  void setBucket(String bucket) {
        InfluxClient2DO.bucket = bucket;
    }
    @Value(value="${spring.influx.org}")
    public  void setOrg(String org) {
        InfluxClient2DO.org = org;
    }
}
