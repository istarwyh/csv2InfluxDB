package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 这个实体类不与数据库表结构对应，而是作为单一实体
 */
@Repository
public class InfluxClientDO {
    /**
     *     如果要批量的得到配置信息应该怎么办呢?-->定义配置类并注入IOC容器,用时再导入
     */
    @Value(value = "${spring.influx.url}")
    private static String host;
    @Value("${spring.influx.database}")
    private static  String database;
    @Value("${spring.influx.retentionPolicy}")
    private static  String retentionPolicy;
    @Value(value = "${spring.influx.user}")
    private static  String username;
    @Value(value = "${spring.influx.password}")
    private static  String password;

    public static final InfluxDBClient CLIENT1 = InfluxDBClientFactory.createV1(
            host,
            username,
            password.toCharArray(),
            database,
            retentionPolicy);

}
