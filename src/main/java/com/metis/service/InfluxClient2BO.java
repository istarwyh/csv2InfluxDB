package com.metis.service;
import java.io.File;
import java.util.List;

import com.metis.dto.LineProtocolDTO;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.metis.entity.InfluxClient2DO;
import org.springframework.stereotype.Service;

import static com.metis.paas.Utils.CSVToList;

/**
 * @author MBin_王艺辉istarwyh
 */
@Service
public class InfluxClient2BO {

    public void csv2InfluxDB(String filePath){
        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<LineProtocolDTO> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");
        WriteApi writeApi = InfluxClient2DO.CLIENT2.getWriteApi();
        for (LineProtocolDTO lineprotocolDTO : list) {
            String data = String.valueOf(lineprotocolDTO);
            System.out.println(lineprotocolDTO);
            try {
                writeApi.writeRecord(InfluxClient2DO.bucket, InfluxClient2DO.org, WritePrecision.NS, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
