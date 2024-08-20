package com.hongda.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/20 23:37
 */
@Service
@Slf4j
public class TestService {

  /*
   * 注解方式重试
   * */
  @Retryable(retryFor = Exception.class, maxAttempts = 3)
  public String testRetry() {
    log.info("进入重试方法内部");
    throw new RuntimeException("发生异常！");
  }

  /*代码方式重试*/
  public String testRetry2() {
    RetryTemplate template = RetryTemplate.builder().maxAttempts(3)
        .fixedBackoff(1000)
        .retryOn(Exception.class)
        .build();

    template.execute(ctx -> {
      log.info("进入重试方法内部");
      return null;
    });
    return null;
  }

  @Recover
  public void recover(Exception e) {
    log.info("进入recover...... ");
  }

}
