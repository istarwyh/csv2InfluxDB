package com.metis.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfluxClientBO2Test {
    /**
     * 测试云端是否正常能接收line protocol的数据
     */
/*
    @Test
    public void demo2InfluxDB(){
        String token = "u2Xonr9XRJliwwsURBCRju4EZeWmToO5hBAwzBryCekmIhQueNKRFwAsaYKdbKLMxwi0QaViG8AHHmBSBTsAyA==";
        String bucket = "05d5f0d32448f000";
        String org = "87e2941a63ad495d";
        InfluxDBClient client = InfluxDBClientFactory.create("https://us-central1-1.gcp.cloud2.influxdata.com", token.toCharArray());
        // 使用人造的可信data代替csv
        String data = "mem,host=host1 used_percent=23.43234543";
        try {
            WriteApi writeApi = client.getWriteApi();
            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/


}