package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Description: InfluxDBClient2DO
 * @author: YiHui
 * @Date: 2020-12-01 17:02
 * @version: 1.0.0
 */
@ToString
public class InfluxDBClient2DO {

    public static String token;
    public static String cloudUrl;

    @Value(value="${spring.influx.token}")
    public  void setToken(String token) {
        InfluxDBClient2DO.token = token;
    }
    @Value(value="${spring.influx.cloudUrl}")
    public  void setCloudUrl(String cloudUrl) {
        InfluxDBClient2DO.cloudUrl = cloudUrl;
    }

    public static InfluxDBClient getInfluxDBClient2(){
        return InfluxDBClientFactory.create(
                cloudUrl,
                token.toCharArray());
    }

}
