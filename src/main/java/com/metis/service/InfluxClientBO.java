package com.metis.service;

import com.metis.config.PropertyUtil;
import com.metis.dto.LineProtocolDTO;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.metis.paas.InfluxClient;
import com.metis.entity.InfluxClient2DO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.metis.paas.Utils.CSVToList;


@Service
public class InfluxClientBO {
    /**
     * 在使用这些参数生成Bean类的时候，我们注入的参数还没有生效，因此会获取不到;在Controller中却可以
     */
//    @Value(value = "${spring.influx.version}")
//    private String v;
//    @Value(value = "${spring.influx.versionBound}")
//    private String versionBound;

    private static final Float VERSION = Float.parseFloat( PropertyUtil.getProperty("spring.influx.version") );
    private static final Float VERSION_BOUND = Float.parseFloat(PropertyUtil.getProperty("spring.influx" +
            ".versionBound"));

    public boolean csv2InfluxDB(String filePath) {
        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<LineProtocolDTO> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");
        WriteApi writeApi = InfluxClient.of(VERSION).getWriteApi();
        try {
            for (LineProtocolDTO lineprotocolDTO : list) {
                String data = String.valueOf(lineprotocolDTO);
                if(VERSION < VERSION_BOUND ){
                    writeApi.writeRecord(WritePrecision.NS, data);}
                else {
                    writeApi.writeRecord(InfluxClient2DO.bucket, InfluxClient2DO.org, WritePrecision.NS, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
