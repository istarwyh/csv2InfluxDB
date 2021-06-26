package com.metis.controller.sign;

import com.metis.dto.ResponseDTO;
import com.metis.dto.sign.SignUserDTO;
import com.metis.entity.BaseUser;
import com.metis.entity.SignUser;
import com.metis.common.impl.CheckUtils;
import com.metis.service.impl.SignService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: SignUserController
 * @Author: wx:istarwyh
 * @Date: 2021-05-29 20:56
 * @Version: ing
 */
@RestController
@RequestMapping(value = "/signUser")
public class SignUserController {

    @Resource
    SignService signService;

    @PostMapping(value = "login")
    public ResponseDTO<?> login(@RequestBody(required = true) SignUserDTO suDTO) {
        if (!CheckUtils.checkLoginParam(suDTO)) {
            return ResponseDTO.forLackParam(suDTO);
        }
        SignUser signUser = SignUser.builder().baseUser(BaseUser.builder().name(suDTO.getUsername()).build())
                .passwd(suDTO.getPassword()).build();
        if (signService.existMatchedUser(signUser)) {
            return ResponseDTO.forSuccess();
        } else {
            return ResponseDTO.forParamEx(signUser);
        }
    }
}
