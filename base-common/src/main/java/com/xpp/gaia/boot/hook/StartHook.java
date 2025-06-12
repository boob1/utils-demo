package com.xpp.gaia.boot.hook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动Hook
 *
 * @author Akira
 * @since 2021/9/28
 */
@Component
@Slf4j
public class StartHook implements ApplicationRunner {

    public static String appName;

    public static String profile;

    public static String appVersion;

    public static String appPackage;

    public static String dingSecret;

    public static String dingCustomRobotToken;

    @Override
    public void run(ApplicationArguments args) {
        log.info("\n" +
                "__  __                 _                _ _           _   _\n" +
                "\\ \\/ /_ __  _ __      / \\   _ __  _ __ | (_) ___ __ _| |_(_) ___  _ __\n" +
                " \\  /| '_ \\| '_ \\    / _ \\ | '_ \\| '_ \\| | |/ __/ _` | __| |/ _ \\| '_ \\\n" +
                " /  \\| |_) | |_) |  / ___ \\| |_) | |_) | | | (_| (_| | |_| | (_) | | | |\n" +
                "/_/\\_\\ .__/| .__/  /_/   \\_\\ .__/| .__/|_|_|\\___\\__,_|\\__|_|\\___/|_| |_|\n" +
                "     |_|   |_|             |_|   |_|");
        log.info("{} [{}] Started......", appName, appVersion);
    }

    @Value("${spring.application.name}")
    public void setAppName(String name) {
        appName = name == null ? "" : name;
    }

    @Value("${spring.profiles.active}")
    public void setProfile(String active) {
        profile = active == null ? "undefined" : active;
    }

    @Value("${spring.application.version}")
    public void setAppVersion(String version) {
        appVersion = version == null ? "0.0.1-SNAPSHOT" : version;
    }

    @Value("${spring.application.package}")
    public void setAppPackage(String packaged) {
        appPackage = packaged == null ? "undefined" : packaged;
    }

    @Value("${constant.dingtalk.secret:ignore}")
    public void setSecret(String secret) {
        dingSecret = secret;
    }
    @Value("${constant.dingtalk.customRobotToken:ignore}")
    public void customRobotToken(String customRobotToken) {
        dingCustomRobotToken = customRobotToken;
    }
}
