package com.hongda.controller;

import com.hongda.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/20 23:36
 */
@RestController
public class TestController {

  @Autowired
  private TestService testService;

  @GetMapping("/retry")
  public void getData() {
    testService.testRetry();
  }


}
