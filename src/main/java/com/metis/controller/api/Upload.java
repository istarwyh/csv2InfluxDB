package com.metis.controller.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: cav2InfluxDBImpl
 * @Author: YiHui
 * @Date: 2020-11-0720:09
 * @Version: ing
 */
public interface Upload<T> {
    /**
     *
     * @param file MultipartFile是Spring类型，代表HTML中form data方式上传的文件，包含二进制数据+文件名称。
     * @param model 模型数据Map,用于模板中数据绑定
     * @return 逻辑视图名称
     **/
    String upload(@RequestParam("file") T file, Model model);
}
