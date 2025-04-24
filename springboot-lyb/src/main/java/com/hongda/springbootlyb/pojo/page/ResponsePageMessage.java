package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import org.springframework.http.HttpStatus;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 11:57
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class ResponsePageMessage<T> {
  private Integer Result_Code;
  private String Result_Desc;
  private PageResultS<T> Result_Data;

  public static <T> ResponsePageMessage<T> success(PageResultS<T> data,String msg) {
    ResponsePageMessage responseMessage = new ResponsePageMessage();
    responseMessage.setResult_Code(HttpStatus.CONTINUE.value());
    responseMessage.setResult_Desc(msg);
    responseMessage.setResult_Data(data);
    return responseMessage;
  }

  public static <T> ResponsePageMessage<T> error(Integer code, String message) {
    ResponsePageMessage responseMessage = new ResponsePageMessage();
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

  public PageResultS<T> getResult_Data() {
    return Result_Data;
  }

  public void setResult_Data(PageResultS<T> result_Data) {
    Result_Data = result_Data;
  }
}
