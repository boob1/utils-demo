package com.hongda.springbootlyb.pojo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/13 23:08
 */
public class ErrorResponse {
  private String message;
  private List<String> details;
  private LocalDateTime timestamp;
  private int status;

  // 构造方法
  public ErrorResponse(String message, List<String> details) {
    this.message = message;
    this.details = details;
    this.timestamp = LocalDateTime.now();
  }

  // Getter 和 Setter 方法
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getDetails() {
    return details;
  }

  public void setDetails(List<String> details) {
    this.details = details;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
