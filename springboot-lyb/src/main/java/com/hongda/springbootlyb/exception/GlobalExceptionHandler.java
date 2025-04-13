package com.hongda.springbootlyb.exception;

import com.hongda.springbootlyb.pojo.ErrorResponse;
import com.hongda.springbootlyb.pojo.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  // 处理表单验证异常
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

    ErrorResponse response = new ErrorResponse("Validation Failed", errors);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class) //什么异常统一处理
  public ResponseMessage handleException(Exception e, HttpServletRequest request,
      HttpServletResponse response) {
    logger.error("统一异常处理！：{}", e.getMessage());
    return ResponseMessage.error(500, e.getMessage());
  }

}
