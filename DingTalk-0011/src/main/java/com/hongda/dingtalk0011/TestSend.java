package com.hongda.dingtalk0011;

import com.hongda.dingtalk0011.utils.DingTalkUtils;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author lyb
 * @Date 2025/5/27 15:36
 */
public class TestSend {
    public static void main(String[] args) {
      // 从配置文件或环境变量获取钉钉机器人信息
      String webhookUrl = "https://oapi.dingtalk.com/robot/send?access_token=08fa30a3d695472661c3bdf8417cd06e29a0874b51c62a4b58953e908f0cc679";
      String secret = "SEC0f27b44d67c14ce3f3775f74a833882ded5b3eebd847731b7037c1c9b7880ece"; // 安全密钥，可选

      // Bug信息
      String bugTitle = "用户登录失败";
      String bugDescription = "测试用户在登录页面输入正确密码后无法登录，错误提示为'密码错误'。\n" +
          "- 复现步骤：\n" +
          "  1. 访问https://example.com/login\n" +
          "  2. 输入用户名：testuser\n" +
          "  3. 输入密码：123456\n" +
          "  4. 点击登录按钮\n" +
          "- 预期结果：成功登录\n" +
          "- 实际结果：显示'密码错误'";
      String bugLevel = "严重";
      String assignedTo = "张三";
      String deadline = "2025-05-25 18:00";
      String bugUrl = "";
      List<String> atMobiles = Collections.singletonList("13157158129"); // 责任人手机号

      // 发送Bug通知
      boolean result = DingTalkUtils.sendBugNotification(
          webhookUrl, secret, bugTitle, bugDescription,
          bugLevel, assignedTo, deadline, bugUrl, atMobiles
      );

      if (result) {
        System.out.println("Bug通知已发送到钉钉群");
      } else {
        System.out.println("Bug通知发送失败");
      }
    }
  }

