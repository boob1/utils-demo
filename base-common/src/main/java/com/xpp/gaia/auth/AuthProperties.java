package com.xpp.gaia.auth;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Auth属性
 *
 * @author Akira
 * @since 2021/11/25
 */
@Data
@ConfigurationProperties(prefix = "xpp.auth")
public class AuthProperties {

    private Boolean authz = false;
    private Redis redis;
    private String excludePaths;
    private String includePaths;
    private String debugTag = "debugging";

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Redis {

        String host;
        Integer port = 6379;
        String password;
        Integer database = 9;
        Integer timeout = 0;
        Integer maxTotal = 5;
        Integer maxIdle = 5;
        Integer minIdle = 0;
    }

}
