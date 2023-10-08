package com.nimang.pupa.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.util.StrUtil;
import com.nimang.pupa.common.tool.webTool.AjaxResult;
import com.nimang.pupa.common.exception.ApiException;
import com.nimang.pupa.common.tool.webTool.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHadnler {

    @Value("${spring.package.base:}")
    private String basePath;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult validExceptionHandler(MethodArgumentNotValidException ex) {
        return AjaxResult.error( ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(",")));
    }

    /**
     * bean参数验证.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validExceptionHandler(BindException ex) {
        return AjaxResult.error( ex.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(",")));
    }

    /**
     * 单个参数验证.
     *
     * @param ex RuntimeException
     * @return String
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult validExceptionHandler(ConstraintViolationException ex) {
        return AjaxResult.error(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(",")));
    }

    /**
     * 登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public AjaxResult noLoginException(NotLoginException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',登录异常.", requestURI, e);
        return new AjaxResult(HttpStatus.UNAUTHORIZED, e.getMessage(), null);
    }

    /**
     * 接口异常
     */
    @ExceptionHandler(ApiException.class)
    public AjaxResult handleApiException(ApiException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',接口发生异常.", requestURI, e);
        return AjaxResult.errorLocation(e.getCode(), e.getMsg(), e.getData(), errorClazz(e));
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.errorLocation(e.getMessage(), errorClazz(e));
    }

    /**
     * 获取产生异常的类及相应行数
     * @param t
     * @return
     */
    public String errorClazz(Throwable t){
        //堆栈跟踪元素数组
        StackTraceElement[] stackTraceElements = t.getStackTrace();
        String exceptionClassTotalName = stackTraceElements[0].toString();

        for(StackTraceElement stackTraceElement:stackTraceElements) {
            //获取含包名称行就是日志打印报错的行数
            if (StrUtil.isNotBlank(basePath) && stackTraceElement.toString().contains(basePath)) {//填写自己的包名称
                //出现报错类的行数位置
                exceptionClassTotalName = stackTraceElement.toString();
                break;
            }
        }
        return exceptionClassTotalName;
    }

}
