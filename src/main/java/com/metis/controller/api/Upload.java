package com.metis.controller.api;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: cav2InfluxDBImpl
 * @Author: YiHui
 * @Date: 2020-11-0720:09
 * @Version: ing
 */
public interface Upload {
    /**
     * MultipartFile是spring类型，代表HTML中form data方式上传的文件，包含二进制数据+文件名称。
     * @param file
     * @return
     */
    boolean upload(@RequestParam("file") MultipartFile file);
}
