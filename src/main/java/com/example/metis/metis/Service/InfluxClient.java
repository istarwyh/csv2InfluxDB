package com.example.metis.metis.Service;

import com.example.metis.metis.Model.InfluxModel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.example.metis.metis.Service.Utils.CSVToList;

/**
 *TODO:把CSVList重写并剥离出来
 */
@Service
public class InfluxClient {
    @Value(value = "${spring.influx.url}")
    private String host;
    @Value("${spring.influx.database}")
    private String database;
    @Value("${spring.influx.retentionPolicy}")
    private String retentionPolicy;
    @Value(value = "${spring.influx.user}")
    private String username;
    @Value(value = "${spring.influx.password}")
    private String password;

    public void csvToInfluxDB(String filePath) {
//        String database = "metis";
//        String retentionPolicy = "autogen";
        InfluxDBClient client = InfluxDBClientFactory.createV1(host,
                username,
                password.toCharArray(),
                database,
                retentionPolicy);

        File dest = new File(filePath);
        String measurementName = "measurementName";
        String fieldKeyValue = "fieldKey=\"fieldValue\"";
        List<InfluxModel> list = CSVToList(dest.getPath(), measurementName, fieldKeyValue);

        System.out.println("*** Write Points ***");

        WriteApi writeApi = client.getWriteApi();

        for (int i = 0; i < list.size(); i++) {
            String data = String.valueOf(list.get(i));
            try {
                writeApi.writeRecord(WritePrecision.NS, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
