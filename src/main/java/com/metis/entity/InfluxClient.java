package com.metis.entity;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import java.util.Objects;

/**
 * 用于生成1.0与2.0不同的客户端
 * 使用单例设计模式，保证在并发情况下也只会有唯一初始化的InfluxClient在工作
 */
public class InfluxClient {
    private static InfluxDBClient client;
    private InfluxClient(int version){
        if( version < 2 ){
            client = InfluxDBClientFactory.createV1(
                    InfluxClient1DO.host,
                    InfluxClient1DO.username,
                    InfluxClient1DO.password.toCharArray(),
                    InfluxClient1DO.database,
                    InfluxClient1DO.retentionPolicy);
        }else{
            client = InfluxDBClientFactory.create( (InfluxClient2DO.cloudUrl),
                    InfluxClient2DO.token.toCharArray());
        }
    }
    public static InfluxDBClient getClient( int version ) {
        if(client == null ){
            synchronized (client){
                if( InfluxClient.client == null ){
                    new InfluxClient(version);
                }
            }
        }
        return client;
    }
}
