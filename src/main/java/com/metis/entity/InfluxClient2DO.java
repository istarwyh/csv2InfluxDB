package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

/**
 * 这个实体类不与数据库表结构对应，而是作为单一实体
 */
@Repository
@ConfigurationProperties(ignoreUnknownFields =false, prefix = "spring.influx")
public class InfluxClient2DO {
    private static String token;
    private static String cloudUrl;
    public static String bucket;
    public static String org;
    public static final InfluxDBClient client2 = InfluxDBClientFactory.create(cloudUrl, token.toCharArray());

}
