package com.hongda.shorturl.controller;

import com.hongda.shorturl.HashUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @Description 短链接生成
 * @Author lyb
 * @Date 2025/4/26 10:29
 */
@Controller
public class UrlController {
  /**
   * 存储长链接和短链接的映射关系（亦可存储在其他中间件中，如：Redis，MySQL等）
   */
  private static Map<String, String> URL_MAP = new HashMap<>();

  /**
   * 生成短链接
   * @param longUrl
   */
  @PostMapping("/generate")
  @ResponseBody
  public String generate(@RequestParam String longUrl) {
    String shortUrl = HashUtils.hashToBase62(longUrl);
    URL_MAP.put(shortUrl, longUrl);
    return shortUrl;
  }

  /**
   * 短链接点击跳转
   * @param shortURL
   */
  @GetMapping("/{shortURL}")
  public String redirect(@PathVariable String shortURL) {
    if (URL_MAP.containsKey(shortURL)) {
      return "redirect:" + URL_MAP.get(shortURL);
    } else {
      return "redirect:/";
    }
  }

  /**
   * 发送邮件
   */

  @GetMapping("/sendMeg")
  @ResponseBody
  public String sendMeg(@RequestParam(required = false) String email,
      @RequestParam(required = false) String msg) {
    if (email == null || email.isEmpty()) {
      return "参数错误：email不能为空";
    }
    if (msg == null || msg.isEmpty()) {
      return "参数错误：msg不能为空";
    }
    return "发送成功: " + email + ": " + msg;
  }
}
