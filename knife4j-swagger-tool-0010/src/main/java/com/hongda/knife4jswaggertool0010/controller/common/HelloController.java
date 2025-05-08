package com.hongda.knife4jswaggertool0010.controller.common;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hongda.knife4jswaggertool0010.pojo.UptModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2025/5/8 14:15
 */
@RequestMapping("common")
@RestController
public class HelloController {

  /**
   * 利用knife4j的@ApiOperationSupport注解，可以自定义接口的作者和排序
   * @return
   */
  @ApiOperationSupport(author="张三",order=2)
  @GetMapping("/hello")
  public String hello(){
    return"hello";
  }

  @ApiOperationSupport(author="张三",order=2)
  @GetMapping("/hi")
  public String hi(@RequestParam("name") String name,@RequestParam("age") int age){
    return"hello----"+name+"---"+age;
  }

  @ApiOperation(value="新增Model接口1")
  @ApiOperationSupport(ignoreParameters = {"uptModel.id"})
  @PostMapping("/insertMode1")
  public List<UptModel> insertModel1(@ApiParam @RequestBody UptModel uptModel){
    List<UptModel> r=new ArrayList<>();
    r.add(uptModel);
    return r;
  }

  @ApiOperationSupport(author="李四",order=1)
  @GetMapping("/access-appid")
  public String getToken(){
    return"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
  }
}
