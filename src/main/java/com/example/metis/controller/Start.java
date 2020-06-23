package com.example.metis.controller;

import com.example.metis.service.InfluxClient;
import com.example.metis.model.Model_KY;
import com.example.metis.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author : zhaxinchi
 *
 */

@Controller
class Start {
    @Autowired
    private InfluxClient influxClient;

    @RequestMapping("/")
            public String Root(){
            return "upload";

            }
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("up","文件为空上传失败");
            return"upload";
        }

        try{
            byte[] bytes = file.getBytes();
            String folderPath = "./repository/";
            Path filePath = Paths.get(folderPath + file.getOriginalFilename());
            Files.write(filePath, bytes);
            String fileName = file.getOriginalFilename();

            List<List<String>> CsvLists = Utils.readCSV(folderPath + fileName);
            assert CsvLists != null;
            List<Model_KY> modelList = Utils .transfer(CsvLists);

            influxClient.csvToInfluxDB(folderPath + fileName);
            model.addAttribute("messages",modelList);
            return "upload";
        }
        catch (IOException e) {
            e.printStackTrace();

        }

        model.addAttribute("up","上传失败");
        return "upload";
    }

//    public static void main(String[] args) throws IOException {
//       String filePath = "D:\\Metis_File\\";
//        File dest = new File(filePath+"new_data.csv");
//
//      //  List<List<String>> userRoleLists = CSVUtils.readCSV(dest.getPath(), 2);
//
//        List<List<String>> userRoleLists = Utils.readCSV(dest.getPath());
//
//        List<Model_KY> modelList = Utils .transfer(userRoleLists);
//
//        System.out.println(modelList);
//
//
//    }

}
