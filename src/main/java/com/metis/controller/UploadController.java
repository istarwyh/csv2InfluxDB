package com.metis.controller;

import com.metis.controller.api.Upload;
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
import java.util.List;

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
        return "showOf";
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

}
