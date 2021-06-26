package com.metis.common;

import com.metis.dto.LineProtocolDTO;
import com.metis.dto.KeyValueDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    /**
     *     暂时没有配日志结构,只能向控制台打印
     */
    public static void copyToLocal(String folderPath, List<LineProtocolDTO> lineprotocolList) {
        String localFileName = "test.protocol";
        File targetFile = new File(folderPath+localFileName);
        if (!targetFile.exists()) {
            try {
                targetFile.createNewFile();
            } catch (IOException e) {
                System.out.println("无法创建targetFile");
            }
        }
        out(targetFile,lineprotocolList);

    }

    static void out(File targetFile, List<LineProtocolDTO> lineprotocolList) {
        try{
            PrintWriter out = new PrintWriter(targetFile);
            for (Object lineprotocol : lineprotocolList) {
                out.println(lineprotocol);
            }
        }catch (IOException e){
            System.out.println("向targetFile输出发生错误");
        }
    }

    public static List<KeyValueDTO> transfer(List<List<String>> CSVLists){
        List<KeyValueDTO> modelList = new ArrayList<>();

        for(int i =0 ; i < CSVLists.get(0).size() ; i++){
            KeyValueDTO model = new KeyValueDTO(CSVLists.get(0).get(i),CSVLists.get(1).get(i));
            modelList.add(model);
        }
        return modelList;
    }

    public static List<List<String>> readCSV(String filePath) {
        List<CSVRecord> records = getCSVRecord(filePath);
        List<List<String>> values = new ArrayList<>();
        for(int i =0 ; i <2 ; i++){
            CSVRecord record = records.get(i);
            List<String> value = new ArrayList<>();
            for (int j = 0; j < record.size(); j++) {
                value.add(record.get(j));
            }
            values.add(value);
        }
        return values;
    }

    /**
     * csv文件构造成符合line protocol的字符串形式
     * @param filePath 目前没有采用流式处理,需要给出csv文件持久化后的路径,如./repository
     * @param MeasurementName 指定时序数据所属表名
     * @return List<InfluxModel>
     */
    public static List<LineProtocolDTO> CSVToList(String filePath, String MeasurementName) {
        if( filePath == null ){  return null;}

        List<CSVRecord> records = getCSVRecord( filePath );
        if( records == null ){ return null;}

        List<List<String>> values = new ArrayList<>();
        for (CSVRecord record : records) {
            List<String> value = new ArrayList<>();
            for (int j = 0; j < record.size(); j++) {
                value.add(record.get(j));
            }
            values.add(value);
        }

        List<LineProtocolDTO> totalList = new ArrayList<>();
        for(int i =1 ; i<values.size() ; i++) {
//                其实读的正是csv文件中的一行,但是为了解析这一行将其变为了List<String>
            List<String> line = values.get(i);

            LineProtocolDTO lineprotocol = new LineProtocolDTO();

//                给定表名
            lineprotocol.setMeasurementName(MeasurementName);
//                设计TagSet,这里置为空
            lineprotocol.setTagSet("");

            lineprotocol.setFiledSet(designFiledSet(line));

//                加上时间戳,原时间戳位置在csv文件第一位
            lineprotocol.setTimeStamp(String.valueOf(Objects.requireNonNull(transferDate(line.get(0))).getTime()));
//                把每一条lineprotocol插入进去就可以了
            totalList.add(lineprotocol);
        }

        return totalList;

    }

    /**
     * 设计FiledSet,这里采用简单的p1=value1,p2=value2,...p38=value38
     * @param line csv文件的每一行
     * @return String
     */
    static String designFiledSet(List<String> line) {
        StringBuilder sb = new StringBuilder();
        for(int j = 1 ; j<line.size() ; j++) {
//                    对于最后一位filedValue后面不应该有","
            if(j== line.size()-1){
                sb.append("p").append(j).append("=").append(line.get(j));
                break;
            }
            sb.append("p").append(j).append("=").append(line.get(j)).append(",");
        }
        return sb.toString().trim();
    }

    /**
     * 使用引入的org.apache.commons.csv中的CSVParser类包装csv文件构造csv解析器对象
     * 返回null代表发生了未知错误
     */
    static CSVParser CSVParser(String filePath) {
        BufferedReader br = null;
        try {
//            从FileInputStream开始,屏蔽底层细节以及提高效率：节点流->字符流->缓冲字符流
            br = new BufferedReader( new InputStreamReader( new FileInputStream( filePath )));
            return CSVFormat.DEFAULT.parse(br);
        } catch (FileNotFoundException e) {
            System.out.println("CSVParser()读取文件找不到");
        } catch (IOException e) {
            System.out.println("调用CSVFormat转换BufferedReader失败");
        }
        // 对于同一个文件只需要关闭最后一个包装流就可以了，最外观的流会调用其他流的close()
        // TODO：这里应该用try-with-resources,但是貌似不能关？需要注册Java钩子什么等到程序结构结束了再关
        return null;
    }

    /**
     * 返回null代表发生了未知错误
     */
    static List<CSVRecord> getCSVRecord(String filePath) {
        List<CSVRecord> records = null;
        try {
            records = Objects.requireNonNull(CSVParser(filePath),"CSVParser意外为空,可能因为路径无效").getRecords();
            return Objects.requireNonNull(records);

        } catch (IOException e) {
            System.out.println(e + "\ngetCSVRecord()调用csvParser的getRecords()失败");
        }
        return null;
    }

    /**
     * 返回null代表发生了未知错误
     */
    static Date transferDate(String Date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(Date);
        } catch (ParseException e) {
            System.out.println("transferDate() Date转换异常");
        }
        return null;
    }
}
