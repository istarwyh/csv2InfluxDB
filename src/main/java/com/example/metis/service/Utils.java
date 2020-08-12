package com.example.metis.service;

import com.example.metis.model.LineProtocolModel;
import com.example.metis.model.KeyValueModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    /**
     *     暂时没有配日志结构,只能向控制台打印
     */
    public static void copyToLocal(String folderPath,List<LineProtocolModel> lineprotocolList){
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
    public static void out(File targetFile,List<LineProtocolModel> lineprotocolList){
        try{
            PrintWriter out = new PrintWriter(targetFile);
            for (Object lineprotocol : lineprotocolList) {
                out.println(lineprotocol);
            }
        }catch (IOException e){
            System.out.println("向targetFile输出发生错误");
        }
    }

    /**
     *
     * @param CSVLists
     * @return
     */
    public static List<KeyValueModel> transfer(List<List<String>> CSVLists){
        List<KeyValueModel> modelList = new ArrayList<>();

        for(int i =0 ; i < CSVLists.get(0).size() ; i++){

            KeyValueModel model = new KeyValueModel(CSVLists.get(0).get(i),CSVLists.get(1).get(i));
            modelList.add(model);

        }
        return modelList;
    }

    public static List<List<String>> readCSV(String filePath) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);

            List<List<String>> values = new ArrayList<>();

            List<CSVRecord> records = parser.getRecords();

            for(int i =0 ; i <2 ; i++){
                CSVRecord record = records.get(i);
                List<String> value = new ArrayList<>();
                for (int j = 0; j < record.size(); j++) {

                    value.add(record.get(j));
                }
                values.add(value);
            }
            return values;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }

    /**
     * csv文件构造成符合line protocol的字符串形式
     * @param filePath 目前没有采用流式处理,需要给出csv文件持久化后的路径,如./repository
     * @param MeasurementName 指定时序数据所属表名
     * @return List<InfluxModel>
     */
    public static List<LineProtocolModel> CSVToList(String filePath, String MeasurementName) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        List<LineProtocolModel> lineprotocolModelList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);

            List<List<String>> values = new ArrayList<>();

            List<CSVRecord> records = parser.getRecords();

            for (CSVRecord record : records) {
                List<String> value = new ArrayList<>();
                for (int j = 0; j < record.size(); j++) {
                    value.add(record.get(j));
                }
                values.add(value);
            }


            for(int i =1 ; i<values.size() ; i++) {
//                其实读的正是csv文件中的一行,但是为了解析这一行将其变为了List<String>
                List<String> line = values.get(i);

                LineProtocolModel lineprotocol = new LineProtocolModel();

//                给定表名
                lineprotocol.setMeasurementName(MeasurementName);
//                设计TagSet,这里置为空
                lineprotocol.setTagSet("");

                lineprotocol.setFiledSet(designFiledSet(line));

//                加上时间戳,原时间戳位置在csv文件第一位
                lineprotocol.setTimeStamp(String.valueOf(transferDate(line.get(0)).getTime()));

                lineprotocolModelList.add(lineprotocol);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return lineprotocolModelList;

    }

    /**
     * 设计FiledSet,这里采用简单的p1=value1,p2=value2,...p38=value38
     * @param line csv文件的每一行
     * @return String
     */
    public static String designFiledSet(List<String> line){
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

    public static Date transferDate(String Date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(Date);

    }


}
