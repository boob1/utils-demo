package com.xpp.gaia.auth.web;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.xpp.gaia.boot.hook.StartHook;
import com.xpp.gaia.boot.utils.DingTalkWringSendUtil;
import com.xpp.gaia.boot.utils.SpringUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 功能描述:  错误日志拦截
 *
 * @param:
 * @return:
 * @auther: magicalOnion
 * @date: 2020/5/8 10:58
 */
@Component
@Slf4j
public class LogHandleFilter extends Filter<ILoggingEvent> {
    private static final List<String> filterErrorList = new ArrayList<>();

    static {
        filterErrorList.add("访问令牌校验不通过");
        filterErrorList.add("请使用微信或App访问");
        filterErrorList.add("无效的访问令牌");
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event != null && !Level.ERROR.equals(event.getLevel())) {
            return FilterReply.NEUTRAL;
        }
        String dingSecret = StartHook.dingSecret;
        String dingCustomRobotToken = StartHook.dingCustomRobotToken;
        if ("ignore".equals(dingSecret)){
            return FilterReply.ACCEPT;
        }
        try {
            String activeProfile = SpringUtil.getActiveProfile();
            String applicationName = SpringUtil.getSpringApplicationName();
            if (!StringUtils.isBlank(activeProfile)&&"dev".equals(activeProfile)){
                return FilterReply.ACCEPT;
            }
            String msg = "【" + applicationName + "】" + "【" + activeProfile + "】" + truncateString(convertEventWithError(event));
            for (String filterError : filterErrorList) {
                if (msg.contains(filterError)) {
                    return FilterReply.ACCEPT;
                }
            }
            DingTalkWringSendUtil.send(msg, dingCustomRobotToken, dingSecret);
        } catch (Exception e) {
            log.info("【log.error】", e);
        }
        return FilterReply.ACCEPT;
    }

    public String convertEventWithError(ILoggingEvent event) {
        try {
            // 检查-------------------------------------------------------
            if (event == null)
                return "";
            if (StringUtils.isBlank(event.getMessage()))
                return "";
            // 获取源Msg替换结果-------------------------------------------
            String msg = printReplace(event.getMessage(), event.getArgumentArray());
            // 尝试获取错误日志--------------------------------------------
            IThrowableProxy err = event.getThrowableProxy();
            Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
            StringBuilder errStr = null;
            if (err != null) {
                errStr = new StringBuilder();
                errStr.append(err.getClassName());
                errStr.append(" : ");
                errStr.append(err.getMessage());
                errStr.append("  "); // \n
                StackTraceElementProxy[] errTrack = err.getStackTraceElementProxyArray();
                for (StackTraceElementProxy stackTraceElementProxy : errTrack) {
                    errStr.append(stackTraceElementProxy.getStackTraceElement());
                    errStr.append("  "); // \n
                }
                errStr.delete(errStr.length() - 3, errStr.length());
            }
            // 构建Json--------------------------------------------------
            LinkedHashMap<String, String> result = new LinkedHashMap();
            LoggerContextVO context = event.getLoggerContextVO();
//            result.put("serverName", context == null ? "default" : context.getName());
            result.put("logTime", LocalDateTime.now().toString() + "Z");
            result.put("level", event.getLevel().levelStr);
            result.put("thread", event.getThreadName());
            result.put("logger", event.getLoggerName());
            result.put("msg", msg);
            if (null != mdcPropertyMap && mdcPropertyMap.size() > 0) {
                result.putAll(mdcPropertyMap);
            }
            if (errStr != null && errStr.length() != 0)
                result.put("err", errStr.toString());
            // 返回--------------------------------------------------
            return (new ObjectMapper()).writeValueAsString(result) + " "; // \n
        } catch (Throwable e) {
            try {
                return (new ObjectMapper()).writeValueAsString(ImmutableMap.of("err", "ESEncoder日志工具错误:" + e.toString())) + " "; // \n
            } catch (Throwable e2) {
                return "{\"err\":\"ESEncoder日志工具错误\"}"; // \n
            }
        }
    }


    private static String printReplace(String first, Object... replaces) {
        try {
            int replaceLen = 0;
            if (StringUtils.isBlank(first))
                return first;
            if (replaces == null || (replaceLen = replaces.length) == 0)
                return first;

            StringBuilder result = new StringBuilder();
            int replaceIdx = 0;
            int curCpIdx = 0;

            Matcher m = Pattern.compile("\\{\\}").matcher(first);
            while (m.find()) {
                if (replaceIdx < replaceLen) {
                    result.append(first.substring(curCpIdx, m.start()));
                    result.append(replaces[replaceIdx] == null ? "null" : replaces[replaceIdx].toString());
                    curCpIdx = m.end();
                } else {
                    result.append(first.substring(curCpIdx, first.length()));
                    break;
                }
                replaceIdx++;
            }
            result.append(first.substring(curCpIdx, first.length()));

            return result.toString();
        } catch (Throwable e) {
            return first;
        }
    }

    // 截取字符串到最多1000个字符
    public static String truncateString(String msg) {
        if (msg != null && msg.length() > 1000) {
            return msg.substring(0, 1000);
        }
        return msg;
    }
}
