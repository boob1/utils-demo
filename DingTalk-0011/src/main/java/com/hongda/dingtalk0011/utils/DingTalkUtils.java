package com.hongda.dingtalk0011.utils;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 钉钉消息通知工具类
 * 支持发送文本、Markdown、链接卡片等消息类型到钉钉群
 */
@Slf4j
public class DingTalkUtils {

  private static final RestTemplate restTemplate = new RestTemplate();

  /**
   * 发送文本消息到钉钉群
   * @param webhookUrl 钉钉机器人Webhook地址
   * @param secret 加签密钥（可选，为空则不使用加签）
   * @param content 消息内容
   * @param atMobiles 被@人的手机号列表（可选）
   * @param isAtAll 是否@所有人（可选）
   * @return 发送结果（成功/失败）
   */
  public static boolean sendTextMessage(String webhookUrl, String secret, String content,
      List<String> atMobiles, Boolean isAtAll) {
    // 构建消息体
    JSONObject message = new JSONObject();
    message.put("msgtype", "text");

    JSONObject text = new JSONObject();
    text.put("content", content);
    message.put("text", text);

    // 构建@信息
    JSONObject at = new JSONObject();
    if (atMobiles != null && !atMobiles.isEmpty()) {
      at.put("atMobiles", atMobiles);
    }
    if (isAtAll != null) {
      at.put("isAtAll", isAtAll);
    }
    message.put("at", at);

    return sendMessage(webhookUrl, secret, message);
  }

  /**
   * 发送Markdown格式消息（推荐用于Bug通知）
   * @param webhookUrl 钉钉机器人Webhook地址
   * @param secret 加签密钥（可选，为空则不使用加签）
   * @param title 消息标题
   * @param markdownContent Markdown格式的消息内容
   * @param atMobiles 被@人的手机号列表（可选）
   * @param isAtAll 是否@所有人（可选）
   * @return 发送结果
   */
  public static boolean sendMarkdownMessage(String webhookUrl, String secret, String title,
      String markdownContent, List<String> atMobiles, Boolean isAtAll) {
    JSONObject message = new JSONObject();
    message.put("msgtype", "markdown");

    JSONObject markdown = new JSONObject();
    markdown.put("title", title);
    markdown.put("text", markdownContent);
    message.put("markdown", markdown);

    // 构建@信息
    JSONObject at = new JSONObject();
    if (atMobiles != null && !atMobiles.isEmpty()) {
      at.put("atMobiles", atMobiles);
    }
    if (isAtAll != null) {
      at.put("isAtAll", isAtAll);
    }
    message.put("at", at);

    return sendMessage(webhookUrl, secret, message);
  }

  /**
   * 发送链接卡片消息
   * @param webhookUrl 钉钉机器人Webhook地址
   * @param secret 加签密钥（可选，为空则不使用加签）
   * @param title 消息标题
   * @param text 消息内容（支持Markdown格式）
   * @param messageUrl 点击消息跳转的URL
   * @param picUrl 图片URL（可选）
   * @return 发送结果
   */
  public static boolean sendLinkMessage(String webhookUrl, String secret, String title, String text,
      String messageUrl, String picUrl) {
    JSONObject message = new JSONObject();
    message.put("msgtype", "link");

    JSONObject link = new JSONObject();
    link.put("title", title);
    link.put("text", text);
    link.put("messageUrl", messageUrl);
    if (picUrl != null && !picUrl.isEmpty()) {
      link.put("picUrl", picUrl);
    }
    message.put("link", link);

    return sendMessage(webhookUrl, secret, message);
  }

  /**
   * 发送Bug通知（使用Markdown格式）
   * @param webhookUrl 钉钉机器人Webhook地址
   * @param secret 加签密钥（可选，为空则不使用加签）
   * @param bugTitle Bug标题
   * @param bugDescription Bug详细描述
   * @param bugLevel Bug级别（如：严重/中等/轻微）
   * @param assignedTo 责任人
   * @param deadline 解决期限
   * @param bugUrl Bug详情链接（可选）
   * @param atMobiles 被@人的手机号列表（可选）
   * @return 发送结果
   */
  public static boolean sendBugNotification(String webhookUrl, String secret, String bugTitle,
      String bugDescription, String bugLevel, String assignedTo,
      String deadline, String bugUrl, List<String> atMobiles) {
    // 构建Markdown内容
    StringBuilder markdown = new StringBuilder();
    markdown.append("### 🐞 Bug通知\n\n");
    markdown.append("| 属性       | 详情       |\n");
    markdown.append("|------------|------------|\n");
    markdown.append("| **标题**   | ").append(bugTitle).append(" |\n");
    markdown.append("| **级别**   | ").append(getBugLevelEmoji(bugLevel)).append(" ").append(bugLevel).append(" |\n");
    markdown.append("| **责任人** | @").append(assignedTo).append(" |\n");
    markdown.append("| **期限**   | ").append(deadline).append(" |\n\n");
    markdown.append("**描述**:\n").append(bugDescription).append("\n\n");

    if (bugUrl != null && !bugUrl.isEmpty()) {
      markdown.append("[查看详情](").append(bugUrl).append(")\n\n");
    }

    // 添加@信息到Markdown
    if (atMobiles != null && !atMobiles.isEmpty()) {
      for (String mobile : atMobiles) {
        markdown.append("<@").append(mobile).append("> ");
      }
      markdown.append("\n");
    }

    return sendMarkdownMessage(webhookUrl, secret, "Bug通知: " + bugTitle,
        markdown.toString(), atMobiles, false);
  }

  /**
   * 根据Bug级别返回对应的Emoji
   */
  private static String getBugLevelEmoji(String level) {
    if (level == null) return "";
    level = level.toLowerCase();
    if (level.contains("严重") || level.contains("critical") || level.contains("p0")) {
      return "🔥";
    } else if (level.contains("中等") || level.contains("major") || level.contains("p1")) {
      return "⚠️";
    } else if (level.contains("轻微") || level.contains("minor") || level.contains("p2")) {
      return "🐛";
    }
    return "❓";
  }

  /**
   * 发送消息到钉钉
   */
  private static boolean sendMessage(String webhookUrl, String secret, JSONObject message) {
    try {
      // 如果有secret，生成签名并添加到URL
      if (secret != null && !secret.isEmpty()) {
        long timestamp = System.currentTimeMillis();
        String sign = generateSign(timestamp, secret);
        webhookUrl = webhookUrl + "&timestamp=" + timestamp + "&sign=" + sign;
      }

      // 设置HTTP请求头
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      // 发送请求
      HttpEntity<String> entity = new HttpEntity<>(message.toJSONString(), headers);
      ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);

      // 处理响应
      if (response.getStatusCode() == HttpStatus.OK) {
        JSONObject result = JSON.parseObject(response.getBody());
        Integer errcode = result.getInteger("errcode");
        String errmsg = result.getString("errmsg");

        if (errcode != null && errcode == 0) {
          return true;
        } else {
          return false;
        }
      } else {

        return false;
      }
    } catch (Exception e) {

      return false;
    }
  }

  /**
   * 生成签名（HMAC-SHA256）
   */
  private static String generateSign(long timestamp, String secret) throws NoSuchAlgorithmException,
      UnsupportedEncodingException, InvalidKeyException {
    // 拼接时间戳和secret
    String stringToSign = timestamp + "\n" + secret;

    // 使用HMAC-SHA256算法生成签名
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));

    // 进行URL编码
    return URLEncoder.encode(Base64.getEncoder().encodeToString(signData), "UTF-8");
  }
}
