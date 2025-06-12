package com.xpp.gaia.boot.utils;

import com.alibaba.nacos.common.codec.Base64;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import java.net.URLEncoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: yangtao
 * @CreateTime: 2025-04-17
 * @Description:
 * @Version: 1.0
 */
@Slf4j
public class DingTalkWringSendUtil {

    public static void send(String msg, String dingtalkCustomRobotToken, String dingtalkCustomRobotSecret) {
        try {
            Long timestamp = System.currentTimeMillis();
            System.out.println(timestamp);
            String secret = dingtalkCustomRobotSecret;
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?sign=" + sign + "&timestamp=" + timestamp);
            OapiRobotSendRequest req = new OapiRobotSendRequest();
            //定义文本内容
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(msg);
            //定义 @ 对象
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            // at.setAtUserIds(Arrays.asList(USER_ID));
            //设置消息类型
            req.setMsgtype("text");
            req.setText(text);
            req.setAt(at);
            OapiRobotSendResponse rsp = client.execute(req, dingtalkCustomRobotToken);
            System.out.println(rsp.getBody());
        } catch (Exception e) {
            log.info("【发送钉钉异常】", e);
        }
    }
}

