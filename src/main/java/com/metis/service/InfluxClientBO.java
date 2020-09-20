package com.metis.service;

import com.metis.dto.LineProtocolDTO;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.metis.entity.InfluxClientDO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.metis.paas.Utils.CSVToList;


@Service
public class InfluxClientBO {
    public void csv2InfluxDB(String filePath) {
        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<LineProtocolDTO> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");
        WriteApi writeApi = InfluxClientDO.CLIENT1.getWriteApi();
        for (LineProtocolDTO lineprotocolDTO : list) {
            String data = String.valueOf(lineprotocolDTO);
            try {
                writeApi.writeRecord(WritePrecision.NS, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
