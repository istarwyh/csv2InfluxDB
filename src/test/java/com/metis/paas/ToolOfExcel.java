package com.metis.paas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lkx.util.ExcelUtil;
import com.metis.entity.Comparator2Note;
import com.metis.entity.excel.*;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: ToolOfExcel
 * @Author: YiHui
 * @Date: 2020-11-10 13:52
 * @Version: ing
 */
public class ToolOfExcel {
    public static final String folderPath = "./repository/";
    public static final String folderPath1 = "./repository/CityLevel/";
    public static final String folderPath2 = "./repository/GB2260/";
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
//        县级市归属地级市,还有部分归属省
        List<CountyLevelCity> countyLevelCityList = ExcelUtil.readXls( folderPath1 + "县级市.xlsx",CountyLevelCity.class);
        String city;String cityName;
        for( CountyLevelCity c : countyLevelCityList ){
            city = c.getCity();
//            如果只是indexOf("市"),有可能出现"市中市"这种情况
            cityName = city.substring(0,city.lastIndexOf("市"));
            map.put( cityName , c.getProvince());
        }
        List<RegionOfChina> countyLevelCityMoreList = new ArrayList<>();
        for( int i = regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCounty( checkCountyName+"市" );
                countyLevelCityMoreList.add( regionOfChinaList.get(i));
//                为了防止"东港市"和"东港区"同时存在于regionOfChinaList中的情况
                map.remove(checkCountyName);
                regionOfChinaList.remove(i);
            }
        }

//        市辖区归属市
        List<CountyOfCity> countyOfCityList = ExcelUtil.readXls( folderPath1 + "市辖区.xlsx",CountyOfCity.class);
        String county;
        for( CountyOfCity c : countyOfCityList ){
            county = c.getCounty();
            String countyName = county.substring(0,county.lastIndexOf("区") );
            map.put( countyName ,c.getCity());
        }

        List<RegionOfChina> countyOfCityMoreList = new ArrayList<>();
        for( int i =regionOfChinaList.size() -1;i>=0;i-- ){
            String checkCounty = regionOfChinaList.get(i).getCounty();
//            多亏了这个数据是干净一点的(尾部没有空格),要不然该多判断截到尾部了
            String checkCountyName = checkCounty.substring(0,checkCounty.length()-1);
            if( map.containsKey(checkCountyName )){
                regionOfChinaList.get(i).setCounty( checkCountyName+"区" );
                countyOfCityMoreList.add( regionOfChinaList.get(i));

                map.remove( checkCountyName);
                regionOfChinaList.remove(i);
            }
        }
        if( countyOfCityMoreList.size() < countyOfCityList.size() ){
            System.out.println( countyOfCityList.size()  - countyOfCityMoreList.size() );
        }
//        ExcelUtil.exportExcel(folderPath1+"纯县.xlsx",regionOfChinaList,RegionOfChina.class);
//        来源的中国省市区应当全面!!
//        否则以芜湖县改名湾沚区为例,清洗之后的countyOfCityMoreList中湾沚区将不会加入
//        同时芜湖县将保存在regionOfChinaList中
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
//  如果只是用county.contains("市")判断,像"市北区"这种就会被误判成"县级市"
                if(county.contains("市") && county.lastIndexOf("市") == county.length()-1 ){
                    typeOfRegion.setTypeOfRegion( "县级市");
                }else if(county.contains("区") && county.lastIndexOf("区") == county.length()-1){
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

    @Test
    void comparator2Note() throws Exception {
        List<Comparator2Note> comparator2NoteList = ExcelUtil.readXls( folderPath + "Comparator2Note.xlsx",Comparator2Note.class);
        List<Comparator2Note> resList = new ArrayList<>(3000);
        for( Comparator2Note c : comparator2NoteList ){
            String k1 = c.getK1();
            String k2 = c.getK2();
            if( !k1.equals( k2 )){
                c.setNote( k2 );
            }
            resList.add( c );
        }
        ExcelUtil.exportExcel(folderPath + "C2N.xlsx",resList,Comparator2Note.class);

    }
}
