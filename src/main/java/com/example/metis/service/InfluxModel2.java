package com.example.metis.service;
import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
/**
 * @author MBin_王艺辉istarwyh
 */
public class InfluxModel2 {
    public static void main(String[] args) {
        // You can generate a Token from the "Tokens Tab" in the UI
        String token = "token";
        String bucket = "bucketID";
        String org = "87e2941a63ad495d";

        InfluxDBClient client = InfluxDBClientFactory.create("https://us-central1-1.gcp.cloud2.influxdata.com", token.toCharArray());
        String data = "mem,host=host1 used_percent=23.43234543";
        try (WriteApi writeApi = client.getWriteApi()) {
            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
        }
    }

}
