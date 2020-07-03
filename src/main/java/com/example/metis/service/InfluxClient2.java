package com.example.metis.service;
import java.io.File;
import java.util.List;

import com.example.metis.model.InfluxModel;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import static com.example.metis.service.Utils.CSVToList;

/**
 * @author MBin_王艺辉istarwyh
 */
@Service
@ConfigurationProperties(ignoreUnknownFields =false, prefix = "spring.influx")
public class InfluxClient2 {
    private static String token;
    private static String bucket;
    private static String org;
    private static String cloudUrl;
    private String filePath;

    public void csv2InfluxDB(){
        System.out.println(cloudUrl);

        InfluxDBClient client = InfluxDBClientFactory.create(cloudUrl, token.toCharArray());

        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<InfluxModel> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");

        WriteApi writeApi = client.getWriteApi();

        for (InfluxModel influxModel : list) {
            String data = String.valueOf(influxModel);
            System.out.println(influxModel);
            try {
                writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试云端是否正常能接收line protocol的数据
     */
    public void demo2InfluxDB(){
        String token = "u2Xonr9XRJliwwsURBCRju4EZeWmToO5hBAwzBryCekmIhQueNKRFwAsaYKdbKLMxwi0QaViG8AHHmBSBTsAyA==";
        String bucket = "05d5f0d32448f000";
        String org = "87e2941a63ad495d";
        InfluxDBClient client = InfluxDBClientFactory.create("https://us-central1-1.gcp.cloud2.influxdata.com", token.toCharArray());
        String data = "mem,host=host1 used_percent=23.43234543";
        try {
            WriteApi writeApi = client.getWriteApi();
            writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
