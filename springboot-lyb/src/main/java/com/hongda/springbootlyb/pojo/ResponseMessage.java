package com.hongda.springbootlyb.pojo;

import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/13 22:27
 */
public class ResponseMessage<T> {


  private Integer code;
  private String message;
  private T data;

  private List<String> details;

  public ResponseMessage() {
  }

  public ResponseMessage(String message, List<String> details) {
    this.message = message;
    this.details = details;
  }

  public static <T> ResponseMessage<T> success(T data) {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setCode(HttpStatus.OK.value());
    responseMessage.setMessage("成功");
    responseMessage.setData(data);
    return responseMessage;
  }

  public static <T> ResponseMessage<T> error(Integer code, String message) {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setCode(code);
    responseMessage.setMessage(message);
    return responseMessage;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
