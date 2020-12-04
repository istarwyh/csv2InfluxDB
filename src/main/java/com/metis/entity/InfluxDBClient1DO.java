package com.metis.entity;


import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 这个实体类不与数据库表结构对应，而是作为单一实体
 * 仅仅是将它装在对象的壳中
 * TODO:为什么@Autowired不能用于静态域与方法
 * @Description: InfluxDBClient1DO
 * @author: YiHui
 * @Date: 2020-12-01 16:43
 * @version: 1.0.0
 */

@Repository
@Getter
@Setter
@ToString
public class InfluxDBClient1DO  {
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
        InfluxDBClient1DO.host = host;
    }
    @Value("${spring.influx.database}")
    public  void setDatabase(String database) {
        InfluxDBClient1DO.database = database;
    }
    @Value("${spring.influx.retentionPolicy}")
    public  void setRetentionPolicy(String retentionPolicy) {
        InfluxDBClient1DO.retentionPolicy = retentionPolicy;
    }
    @Value(value = "${spring.influx.user}")
    public  void setUsername(String username) {
        InfluxDBClient1DO.username = username;
    }
    @Value(value = "${spring.influx.password}")
    public  void setPassword(String password) {
        InfluxDBClient1DO.password = password;
    }

    /**
     * 不可以做成静态方法，因为这里应当允许InfluxCBClient对象被修改，那么每一个influxDBclient都应当是一个
     * @return
     */
    public InfluxDBClient getInfluxDBClient1(){
        return InfluxDBClientFactory.createV1(
                host,
                username,
                password.toCharArray(),
                database,
                retentionPolicy);
    }
}