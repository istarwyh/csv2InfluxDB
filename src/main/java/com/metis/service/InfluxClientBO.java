package com.metis.service;

import com.metis.dto.LineProtocolDTO;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.metis.entity.InfluxClient;
import com.metis.entity.InfluxClient1DO;
import com.metis.entity.InfluxClient2DO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.metis.paas.Utils.CSVToList;


@Service
public class InfluxClientBO {
    @Value(value = "${spring.influx.version}")
    private String v;

    public void csv2InfluxDB(String filePath) {
        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<LineProtocolDTO> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");

        int version = Integer.parseInt(v);
        WriteApi writeApi = InfluxClient.getClient( version ).getWriteApi();

        try {
            for (LineProtocolDTO lineprotocolDTO : list) {
                String data = String.valueOf(lineprotocolDTO);
                if(version < 2 ){
                writeApi.writeRecord(WritePrecision.NS, data);}
                else {
                    writeApi.writeRecord(InfluxClient2DO.bucket, InfluxClient2DO.org, WritePrecision.NS, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
