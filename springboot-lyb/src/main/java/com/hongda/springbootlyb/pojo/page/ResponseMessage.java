package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/13 22:27
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class ResponseMessage<T> {


  private Integer Result_Code;
  private String Result_Desc;
  private T Result_Data;



  public ResponseMessage() {
  }


  public static <T> ResponseMessage<T> success(T data,String message) {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setResult_Code(HttpStatus.CONTINUE.value());
    responseMessage.setResult_Desc(message);
    responseMessage.setResult_Data(data);
    return responseMessage;
  }

  public static <T> ResponseMessage<T> error(Integer code, String message) {
    ResponseMessage responseMessage = new ResponseMessage();
    responseMessage.setResult_Code(code);
    responseMessage.setResult_Desc(message);
    return responseMessage;
  }

  public Integer getResult_Code() {
    return Result_Code;
  }

  public void setResult_Code(Integer result_Code) {
    Result_Code = result_Code;
  }

  public String getResult_Desc() {
    return Result_Desc;
  }

  public void setResult_Desc(String result_Desc) {
    Result_Desc = result_Desc;
  }

  public T getResult_Data() {
    return Result_Data;
  }

  public void setResult_Data(T result_Data) {
    Result_Data = result_Data;
  }
}
