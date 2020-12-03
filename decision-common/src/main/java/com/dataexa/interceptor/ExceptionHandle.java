package com.dataexa.interceptor;

import com.dataexa.exception.BusinessException;
import com.dataexa.result.RespResult;
import com.dataexa.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author 胡志成
 * @date 2020/6/2
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(HttpServletRequest request, Exception e) {
        //获取方法url
        String url = request.getRequestURI();
        log.error("报错方法:{}--服务内部错误:{}", url, e);
        //Hibernate Validator验证消息返回
        BindingResult result = null;
        if (e instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        } else if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsg.append(violation.getMessage()).append(",");
                break;
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return RespResult.failWithErrMsg(errorMsg.toString());
        }

        if (result != null) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage()).append(",");
                break;  //只取一条就跳出
            }
            errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
            return RespResult.failWithErrMsg(errorMsg.toString());
        }

        return RespResult.failWithErrMsg(e.getMessage());
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result handle(HttpServletRequest request, BusinessException e) {
        String url = request.getRequestURI();
        log.error("报错方法:{}--业务错误:{}", url, e.getMessage());
        return RespResult.failWithErrMsg(e.getMessage());
    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Result handle(HttpServletRequest request, IllegalArgumentException e) {
        String url = request.getRequestURI();
        log.error("报错方法:{}--参数错误:{}", url, e.getMessage());
        return RespResult.failWithErrMsg(e.getMessage());
    }

}
