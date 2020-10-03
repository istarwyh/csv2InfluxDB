package com.metis.controller;

import com.metis.service.InfluxClientBO;
import com.metis.dto.KeyValueDTO;
import com.metis.paas.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
class UploadController {
    private final InfluxClientBO influxClientBO = new InfluxClientBO();

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("up","文件为空上传失败");
            return"upload";
        }

        try{
            byte[] bytes = file.getBytes();
//            windows下的File.separator是“\”,所以这里不能用；其实是违背了跨平台的初衷
            String folderPath = "./repository/";
            Path filePath = Paths.get(folderPath + file.getOriginalFilename());
            Files.write(filePath, bytes);
            String fileName = file.getOriginalFilename();

            List<List<String>> csvLists = Utils.readCSV(folderPath + fileName);
            List<KeyValueDTO> modelList = Utils.transfer(csvLists);

            influxClientBO.csv2InfluxDB(folderPath + fileName);
            model.addAttribute("lineprotocalData",modelList);
            return "upload";
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("up","上传失败");
        return "upload";
    }

}
