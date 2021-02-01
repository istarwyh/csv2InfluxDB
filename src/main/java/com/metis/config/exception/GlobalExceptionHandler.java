package com.metis.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.metis.config.JsonResult;
import com.metis.config.business.BusinessErrorException;

/**
 * @Description: GlobalExceptionHandler
 * @Author: YiHui
 * @Date: 2021-01-30 21:12
 * @Version: ing
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 打印log
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 缺少请求参数异常
     * 
     * @param ex HttpMessageNotReadableException
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public JsonResult<?> handleHttpMessageNotReadableException(MissingServletRequestParameterException ex) {
        logger.error("缺少请求参数，{}", ex.getMessage());
        return new JsonResult<>(ExceptionEnum.LACK_PARAM.code(), ExceptionEnum.LACK_PARAM.getDesc());
    }

    /**
     * 空指针异常
     * 
     * @param ex NullPointerException
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<?> handleTypeMismatchException(NullPointerException ex) {
        logger.error("空指针异常，{}", ex.getMessage());
        return new JsonResult<>(ExceptionEnum.NULL_VALUE.code(), ExceptionEnum.NULL_VALUE.getDesc());
    }

    /**
     * 拦截业务异常，返回业务异常信息
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<?> handleBusinessError(BusinessErrorException ex) {
        String code = ex.getCode();
        String message = ex.getMessage();
        return new JsonResult<>(code, message);
    }

    /**
     * 系统异常 预期以外异常
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<?> handleUnexpectedServer(Exception ex) {
        logger.error("系统异常:", ex);
        return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
