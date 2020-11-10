package com.metis.controller;

import com.lkx.util.ExcelUtil;
import com.metis.controller.api.Upload;
import com.metis.entity.excel.FromExcel1;
import com.metis.entity.excel.OutOfPovertyRegion;
import com.metis.entity.excel.RegionOfChina;
import com.metis.service.InfluxClientBO;
import com.metis.dto.KeyValueDTO;
import com.metis.paas.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
class UploadController implements Upload {
    private final InfluxClientBO influxClientBO = new InfluxClientBO();
    /**
     *     windows下的File.separator是“\”,所以这里不能用其实是违背了跨平台的初衷
     */
    private final String folderPath = "./repository/";

    /**
     * 这里需要返回view,所以不要加@ResponseBody了
     */
    @RequestMapping("/") public String root(){
        return "test";
    }

    @Override
    @PostMapping("/upload") public boolean upload(@RequestParam(value = "file") MultipartFile file){
        return influxClientBO.csv2InfluxDB(folderPath + file.getOriginalFilename());
    }

    /**
     * RequestParam允许post请求带URL参数,@RequestBody则要求post请求body内发送json,两者可以同时使用
     * MultipartFile是Spring类型,指代的是从form表单提交的文件(直接访问会被当作Get请求而无效),其中getOriginalFilename()是全名,getName()是文件名,getContentType()是文件后缀名
     */
    @PostMapping("/showOf") public String upload(@RequestParam(value = "file") MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("up","文件为空,上传失败");
            return "showOf";
        }
        String filePath = folderPath + file.getOriginalFilename();
        File targetFile = new File(filePath);

        try{
            if( !targetFile.exists()){
                // 将文件流写到服务器本地
                Files.write(Paths.get(filePath), file.getBytes());
            }

            List<List<String>> csvLists = Utils.readCSV(filePath);
            List<KeyValueDTO> keyValueList = Utils.transfer(csvLists);
            model.addAttribute("lineprotocalData",keyValueList);
            return "showOf";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("up","上传失败");
            return "showOf";
        }
    }

    @PostMapping(value = "/test")
    public  String testImport(@RequestParam(value = "file")  MultipartFile file, Model model) throws Exception{
        List<OutOfPovertyRegion> outOfPovertyRegionList = this.getOutOfPoverty(file);
        model.addAttribute("resList",outOfPovertyRegionList);
        return "test";
    }

    private List<OutOfPovertyRegion>  getOutOfPoverty(MultipartFile file) throws Exception{
         /*
          注意读取时文件表头可能因为隐藏的格式问题读取失败,此时可重写
         */
        List<FromExcel1> excelList = ExcelUtil.readXls( file.getBytes(), FromExcel1.class);
        List<RegionOfChina> regionOfChinaList = ExcelUtil.readXls( folderPath+"中国省市区.xlsx",RegionOfChina.class);
        HashMap<String, String> countyCityMap = new HashMap<>(1024);
        for( RegionOfChina row : regionOfChinaList ){
            countyCityMap.put( row.getCounty(),row.getCity());
        }
        List<OutOfPovertyRegion> resList = new ArrayList<>();
        String pattern = "(\\d{4})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(Objects.requireNonNull(file.getOriginalFilename(),"file没有标识"));
        String year = "null";
//        find()这一步与Set的contains()方法不一样,它同时也是寻找匹配的过程.没有这一步将没有分组.
        if( m.find()) {
            year= m.group();
        }
        for (FromExcel1 row : excelList) {
            String[] counties = row.getItem().split("、");
            for (String s : counties) {
                OutOfPovertyRegion res = new OutOfPovertyRegion();
                res.setProvince(row.getProvince());
                res.setCity( countyCityMap.getOrDefault(s,"null"));
                res.setCounty(s);
                res.setOutOfPovertyYear(year);
                resList.add(res);
            }
        }
        ExcelUtil.exportExcel(folderPath+ year +"res.xlsx",resList, OutOfPovertyRegion.class);
        return resList;
    }
}
