package com.hongda.springbootlyb.exception;

import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 统一处理异常处理
 * @Author lyb
 * @Date 2025/4/13 22:39
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // 处理表单验证异常

  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.OK)
  public ResponseMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    BindingResult bindingResult = ex.getBindingResult();
    StringBuffer sb = new StringBuffer("校验失败：");
    for(FieldError fieldError :bindingResult.getFieldErrors()){
      sb.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(",");
    }
    String msg = sb.toString();
    return  ResponseMessage.error(500,msg);
  }

  @ExceptionHandler(Exception.class) //什么异常统一处理
  public ResponseMessage handleException(Exception e, HttpServletRequest request,
      HttpServletResponse response) {
    logger.error("统一异常处理！：{}", e.getMessage());
    return ResponseMessage.error(500, e.getMessage());
  }

}
