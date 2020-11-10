package com.metis.paas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lkx.util.Excel;
import com.lkx.util.ExcelUtil;
import com.metis.entity.excel.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description: ToolOfExcel
 * @Author: YiHui
 * @Date: 2020-11-10 13:52
 * @Version: ing
 */
public class ToolOfExcel {
    private static final String folderPath1 = "./repository/CityLevel/";
    private static final String folderPath2 = "./repository/GB2260/";
    @Test
    void json2ExcelGB2260() throws Exception{
        int startYear = 1980;
        for( int i = 0; i < 2020 - startYear+1;i++){
            List<CodeOfRegion> codeOfRegionList = new ArrayList<>(1024);

            String curYear = String.valueOf (startYear + i);
            File jsonFile = ResourceUtils.getFile( folderPath2 + curYear+ ".json" );
            String json = FileUtils.readFileToString(jsonFile);
            // json 类型的String字符串转换为json数组
            JSONArray jsonArray = JSON.parseArray(json);
            // 遍历json数组中的json对象并解析填充
            for( Object obj : jsonArray ){
                JSONObject jObj = (JSONObject) obj;
                String code = jObj.getString("code");
                String name = jObj.getString("name");
                CodeOfRegion codeOfRegion = new CodeOfRegion();
                codeOfRegion.setRegionCode( code );
                codeOfRegion.setCounty( name );
                codeOfRegionList.add( codeOfRegion );
            }
            ExcelUtil.exportExcel( folderPath2 + "res/" + curYear+"codeOfRegion.xlsx",codeOfRegionList,CodeOfRegion.class);

        }
    }
    @Test
    List<List<RegionOfChina>> getAllTypeList() throws Exception{
        List<RegionOfChina> regionOfChinaList = ExcelUtil.readXls( folderPath1+"中国省市区.xlsx",RegionOfChina.class);
        HashMap<String, String> map = new HashMap<>(1024);
//        县级市归属省
        List<CountyLevelCity> countyLevelCityList = ExcelUtil.readXls( folderPath1 + "县级市.xlsx",CountyLevelCity.class);
        String city;String cityName;
        for( CountyLevelCity c : countyLevelCityList ){
            city = c.getCity();
            cityName = city.substring(0,city.indexOf("市"));
            map.put( cityName , c.getProvince());
        }
        List<RegionOfChina> countyLevelCityMoreList = new ArrayList<>();
        for( int i =regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCity( checkCountyName+"市" );
                regionOfChinaList.get(i).setCounty( checkCountyName+"市" );
                countyLevelCityMoreList.add( regionOfChinaList.get(i));
                regionOfChinaList.remove(i);
            }
        }

//        市辖区归属市
        List<CountyOfCity> countyOfCityList = ExcelUtil.readXls( folderPath1 + "市辖区.xlsx",CountyOfCity.class);
        String county;
        for( CountyOfCity c : countyOfCityList ){
            county = c.getCounty();
            String countyName = county.substring(0,county.indexOf("区") );
            map.put( countyName ,c.getCity());
        }

        List<RegionOfChina> countyOfCityMoreList = new ArrayList<>();
        for( int i =regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
//            多亏了这个数据是干净一点的,要不然该多判断截到尾部了
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCounty( checkCountyName+"区" );
                countyOfCityMoreList.add( regionOfChinaList.get(i));
                regionOfChinaList.remove(i);
            }
        }
        List<List<RegionOfChina>> joinList = new ArrayList<>();
        joinList.add( countyLevelCityMoreList);
        joinList.add( countyOfCityMoreList);
        joinList.add( regionOfChinaList );
        return joinList;
    }

    @Test
    void getTypeOfRegion() throws Exception{
        List<List<RegionOfChina>> allTypeList = getAllTypeList();
         List<TypeOfRegion> typeOfRegionList = new ArrayList<>(1024);
         for( int i =0;i<3;i++){
            for( RegionOfChina r : allTypeList.get(i) ){
                TypeOfRegion typeOfRegion = new TypeOfRegion();
                typeOfRegion.setRegionCode( r.getRegionCode() );
                typeOfRegion.setProvince( r.getProvince() );
                typeOfRegion.setCity( r.getCity() );
                String county = r.getCounty();
                typeOfRegion.setCounty( county );

                if(county.contains("市")){
                    typeOfRegion.setTypeOfRegion( "县级市");
                }else if(county.contains("区")){
                    typeOfRegion.setTypeOfRegion( "市辖区" );
                }else{
                    typeOfRegion.setTypeOfRegion("县");
                }
                typeOfRegionList.add( typeOfRegion);
            }
         }
//        输出结果并没有排序,不过排序的事,excel很擅长!
        ExcelUtil.exportExcel(folderPath1+"type.xlsx",typeOfRegionList,TypeOfRegion.class);
    }
}
