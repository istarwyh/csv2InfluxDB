package com.metis.service.client;

import com.influxdb.client.InfluxDBClientFactory;
import com.metis.infrastructure.PropertyUtil;
import com.metis.dto.LineProtocolDTO;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.metis.infrastructure.Utils.CSVToList;


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

    /**
     * 将指定位置的时序数据发送到InfluxDB
     */
    public boolean csv2InfluxDB(String filePath) {
        File dest = new File(filePath);
        String measurementName = "measurementName";
        List<LineProtocolDTO> list = CSVToList(dest.getPath(), measurementName);

        System.out.println("*** Write Points ***");
        WriteApi writeApi = this.getWriteApi();

        try {
            for (LineProtocolDTO lineprotocolDTO : list) {
                String data = String.valueOf(lineprotocolDTO);
                if(VERSION < VERSION_BOUND ){
                    writeApi.writeRecord(WritePrecision.NS, data);}
                else {
                    String bucket = PropertyUtil.getProperty("spring.influx.bucket");
                    String org = PropertyUtil.getProperty("spring.influx.org") ;
                    writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     *  * TODO:为什么@Autowired不能用于静态域与方法
     *  这里不应该因为参数多就选择用一个类把参数包起来。
     *  因为通过工厂方法生产InfluxDBClient,区别只是V1,V2. InfluxDBClient本身已经是工厂类的对象
     *      而两个版本的客户端并没有共性，不能抽象出类，即参数再多也成为不了某个新公共的类成员或对象属性
     */
    private WriteApi getWriteApi(){
        WriteApi writeApi;
        if(VERSION < VERSION_BOUND ){
            String host = PropertyUtil.getProperty("spring.influx.url");
            String username = PropertyUtil.getProperty("spring.influx.database");
            String password = PropertyUtil.getProperty("spring.influx.retentionPolicy");
            String database = PropertyUtil.getProperty("spring.influx.user");
            String retentionPolicy = PropertyUtil.getProperty("spring.influx.password");
            writeApi = InfluxDBClientFactory.createV1(
                    host,
                    username,
                    password.toCharArray(),
                    database,
                    retentionPolicy).getWriteApi();
        }else{
            String token = PropertyUtil.getProperty("spring.influx.token");
            String cloudUrl = PropertyUtil.getProperty("spring.influx.cloudUrl");
            writeApi = InfluxDBClientFactory.create(
                    cloudUrl,
                    token.toCharArray()).getWriteApi();
        }
        return writeApi;
    }
}
