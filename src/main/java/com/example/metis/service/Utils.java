package com.example.metis.service;

import com.example.metis.model.InfluxModel;
import com.example.metis.model.Model_KY;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : zhaxinchi
 * @date : 2020/6/2
 */


public class Utils {


//    public static void main(String[] args) throws ParseException {
//
//        String filePath1 = "/Users/huangjiawei/Downloads/";
//        File dest = new File(filePath1+"new_data.csv");
//       String filePath = dest.getPath();
//        BufferedReader bufferedReader = null;
//        InputStreamReader inputStreamReader = null;
//        FileInputStream fileInputStream = null;
//
//        List<InfluxModel> list = CSVToList(filePath,"measurementName","FieldKey=\"FieldValue\"");
//        System.out.println("测试数据  "+list.get(2));
//        System.out.println("测试数据  "+list.get(3));
//        String fileName = "test.line protocol";
//        try {
//            PrintWriter out = new PrintWriter(filePath1+fileName);
//            for(int i =0;i<list.size();i++){
//                out.println(list.get(i));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            fileInputStream = new FileInputStream(filePath);
//            inputStreamReader = new InputStreamReader(fileInputStream);
//            bufferedReader = new BufferedReader(inputStreamReader);
//
//            CSVParser parser = CSVFormat.DEFAULT.parse(bufferedReader);
//
//            List<List<String>> values = new ArrayList<>();
//
//            List<CSVRecord> records = parser.getRecords();
//
//            for(int i =0 ; i <records.size() ; i++){
//                CSVRecord record = records.get(i);
//                List<String> value = new ArrayList<>();
//                for (int j = 0; j < record.size(); j++) {
//
//                    value.add(record.get(j));
//                    // System.out.println(record.get(j));
//                }
//                values.add(value);
//            }
//            List<InfluxModel> influxModelList = new ArrayList<>();
//            for(int i =1 ; i<values.size() ; i++) {
//                List<String> list_cur = values.get(i);
//                InfluxModel influxModel = new InfluxModel();
//                influxModel.setTimeStamp(String.valueOf(transferDate(list_cur.get(0)).getTime()));
//                StringBuilder stringBuilder = new StringBuilder();
//                for(int j = 1 ; j<list_cur.size() ; j++) {
//                    stringBuilder.append(list_cur.get(j)+" ");
//                }
//                influxModel.setP(stringBuilder.toString().trim());
//                influxModelList.add(influxModel);
//            }
//
//
//        } catch (IOException e) {
//
//        }finally {
//            //关闭流
//            if (bufferedReader != null) {
//                try {
//                    bufferedReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (inputStreamReader != null) {
//                try {
//                    inputStreamReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fileInputStream != null) {
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//
//
//    }





    public static List<Model_KY> transfer(List<List<String>> CsvLists){
        List<Model_KY> modelList = new ArrayList<>();

        for(int i =0 ; i < CsvLists.get(0).size() ; i++){

            Model_KY model = new Model_KY(CsvLists.get(0).get(i),CsvLists.get(1).get(i));
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
                    // System.out.println(record.get(j));
                }
                values.add(value);
            }
           // System.out.println(values);
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



    //文件转换成数组
    public static List<InfluxModel> CSVToList(String filePath, String measurementName, String fieldKeyValue) {
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        List<InfluxModel> influxModelList = new ArrayList<>();
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
                    // System.out.println(record.get(j));
                }
                values.add(value);
            }


            for(int i =1 ; i<values.size() ; i++) {
                List<String> list_cur = values.get(i);
                InfluxModel influxModel = new InfluxModel();
                influxModel.setTimeStamp(String.valueOf(transferDate(list_cur.get(0)).getTime()));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(measurementName).append(" ");
                for(int j = 1 ; j<list_cur.size() ; j++) {
                    if(j==list_cur.size()-1){
                        stringBuilder.append("p").append(j).append("=").append(list_cur.get(j));
                        break;
                    }
                    stringBuilder.append("p").append(j).append("=").append(list_cur.get(j)).append(",");
                }

                influxModel.setP(stringBuilder.toString().trim());
                influxModelList.add(influxModel);
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

        return influxModelList;

    }


    public static Date transferDate(String Date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(Date);

    }


}
