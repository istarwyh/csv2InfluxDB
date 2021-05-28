package com.metis.config.exception;

import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.metis.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 仿造BasicErrorController 实现自适应异常(如果是浏览器请求返回网页,否则返回数据)
 *               GlobalExceptionHandler forward对应请求被这个Controller拦截
 * @Author: wx:istarwyh
 * @Date: 2021-02-24 19:44
 * @Version: ing
 */
@Controller
public class AdaptiveExceptionController {


    @RequestMapping(value = "/error/500",produces = MediaType.TEXT_HTML_VALUE)
    public String unexpectedErrorHtml(){
        return "error/500";
    }

    /**
     * 教学目的
     * @param request 在客户端向服务器请求一次就创建一个request对象，并且存储了请求的信息
     * @param response Servlet响应用另一个对象Response对象。
     */
    @RequestMapping(value = "/error/500")
    @ResponseBody
    public ResponseDTO<?> unexpectedError(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(getStatus(request).value());
        HashMap<Integer, String> map = new HashMap<>(8);
        map.put(response.getStatus(),request.getRequestURI());
        return ResponseDTO.builder().data(map).code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .msg(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build();
    }

    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
