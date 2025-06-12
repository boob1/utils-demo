package com.xpp.gaia.redis;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis Lettuce Properties
 *
 * @author Akira
 * @since 2021/11/23
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisLettuceProperties {

    private String host;
    private int port = 6379;
    private int database = 0;
    private String password;
    private long timeout = 1000;
    private Cluster cluster;
    private Lettuce lettuce;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Cluster {
        private List<String> nodes;
        private Integer periodicRefresh = 1; //分钟
        private Integer maxRedirects = 3;
        private Integer triggersTimeout = 30;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Lettuce {

        @NoArgsConstructor
        @Getter
        @Setter
        public class Pool {
            private int maxIdle;
            private int minIdle;
            private int maxActive;
            private long maxWait;
        }
    }

}
