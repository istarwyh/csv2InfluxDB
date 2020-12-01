package com.metis.paas;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.metis.config.PropertyUtil;
import com.metis.entity.InfluxClient1DO;
import com.metis.entity.InfluxClient2DO;

/**
 * 用于生成1.0与2.0不同的客户端
 * 使用单例设计模式，保证在并发情况下也只会有唯一初始化的InfluxClient在工作
 * @author MBin_王艺辉istarwyh
 */
public class InfluxClient {
    private static final String VERSION_BOUND = PropertyUtil.getProperty("spring.influx.versionBound");

    public static InfluxDBClient of(float version){
        if( version < Float.parseFloat(VERSION_BOUND) ){
            return InfluxDBClientFactory.createV1(
                    InfluxClient1DO.host,
                    InfluxClient1DO.username,
                    InfluxClient1DO.password.toCharArray(),
                    InfluxClient1DO.database,
                    InfluxClient1DO.retentionPolicy);
        }else{
           return InfluxDBClientFactory.create( (InfluxClient2DO.cloudUrl),
                    InfluxClient2DO.token.toCharArray());
        }
    }
}
