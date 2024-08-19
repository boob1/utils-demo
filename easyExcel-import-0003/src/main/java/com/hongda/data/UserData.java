package com.hongda.data;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 17:09
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserData {



  private String userName;
  private String userPhone;

  private String gender;

  private String userEmail;

  private Integer age;



  private String userAddress;




}
