package com.metis.config;

import com.metis.config.business.BusinessStatusEnum;
import com.metis.config.exception.ExceptionMsgEnum;
import com.metis.config.exception.NonBusinessRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: @RestControllerAdvice => @ControllerAdvice + @ResponseBody
 *               动态代理盒子模型包裹下:Controller->Aspect->ControllerAdvice->Interceptor->filter
 *               404的错误信息被SpringBoot自己实现了全局自适应异常,即如果浏览器访问找不到会返回对应的html,
 *               APP访问找不到则返回对应的json.这里内部类中也仿照实现.
 * @Author: wx:istarwyh
 * @Date: 2021-01-30 21:12
 * @Version: ing
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 打印log
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常 预期以外异常
     *
     * @param ex Exception 优先级低
     *            因为handleUnexpectedServer要forward到其他url,所以不能适用@ResponseBody,后面单独建立内部类
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleUnexpectedServer(Exception ex) {
        logger.error("不知道啥异常,总之系统异常:", ex);
        //        forward是服务器内部重定向,程序收到请求后重新定向到另一个服务器内部URL,客户机并不知道;
        //        redirect则是服务器收到请求后发送302与新的URL给客户,客户将再请求一次
        //        "/"是根路径之意;如果使用"error/500"则是相对路径之意,会接在上次请求"相对根路径"的后面
        return "forward:/error/500";
    }

    /**
     * 1. todo 内部类被编译后等同于另一个类,但是为什么会优先匹配内部类中的mapper?
     * 2. @ControllerAdvice不会作用到内部类中,大概因为动态代理不会针对内部类生成代理对象？-
     */
    @RestControllerAdvice
    public static class AdaptiveExceptionHandler {
        /**
         * 缺少请求参数异常
         *
         * @param ex HttpMessageNotReadableException
         */
        @ExceptionHandler(MissingServletRequestParameterException.class)
        @ResponseStatus(value = HttpStatus.BAD_REQUEST)
        public ResponseDTO<?> handleHttpMessageNotReadableException(MissingServletRequestParameterException ex) {
            logger.error("缺少请求参数，{}", ex.getMessage());
            return ResponseDTO.forLackParam();
        }

        /**
         * 空指针异常
         *
         * @param ex NullPointerException
         */
        @ExceptionHandler(NullPointerException.class)
        @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
        public ResponseDTO<?> handleTypeMismatchException(NullPointerException ex) {
            logger.error("空指针异常，{}", ex.getMessage());
            return ResponseDTO.forNullValue();
        }

        /**
         * 拦截自定义的非业务异常，返回可帮助排查问题的异常信息
         *
         * @param ex NonBusinessRuntimeException
         */
        @ExceptionHandler(NonBusinessRuntimeException.class)
        @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
        public ResponseDTO<?> handleBusinessError(NonBusinessRuntimeException ex) {
            return ResponseDTO.ofOriginal(ex.getCode(), ex.getMessage());
        }
    }

}
