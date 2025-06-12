package com.xpp.gaia.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 * Redis自动配置
 *
 * @author Akira
 * @since 2021/11/22
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisLettuceProperties.class)
@ConditionalOnClass(name = {"org.springframework.data.redis.connection.RedisConnectionFactory"})
public class RedisConfiguration {

    @Autowired
    RedisLettuceProperties redisLettuceProperties;


    /**
     * 单机版配置
     *
     * @return RedisStandaloneConfiguration
     */
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisLettuceProperties.getDatabase());
        redisStandaloneConfiguration.setHostName(redisLettuceProperties.getHost());
        redisStandaloneConfiguration.setPort(redisLettuceProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisLettuceProperties.getPassword()));
        log.info("Initializing Gaia Redis standalone at database[{}]", redisLettuceProperties.getDatabase());
        return redisStandaloneConfiguration;
    }

    /**
     * 集群版配置
     *
     * @return RedisClusterConfiguration
     */
    public RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        // 解析节点配置（格式：host:port,host:port）
        List<String> nodes = redisLettuceProperties.getCluster().getNodes();
        for (String node : nodes) {
            String[] parts = StringUtils.split(node, ":");
            assert (StringUtils.hasLength(parts[0]));
            clusterConfig.clusterNode(parts[0], Integer.parseInt(parts[1]));
        }
        clusterConfig.setPassword(redisLettuceProperties.getPassword());
        clusterConfig.setMaxRedirects(redisLettuceProperties.getCluster().getMaxRedirects());
        return clusterConfig;
    }

    /**
     * 单机模式-客户端配置
     *
     * @return LettuceClientConfiguration
     */
//    @Bean
//    @ConditionalOnClass(name = "org.apache.commons.pool2.impl.GenericObjectPoolConfig")
    public LettuceClientConfiguration lettuceClientStandaloneConfiguration() {
        // 客户端选项
        ClientOptions clientOptions = ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS) // 断开时拒绝命令
                .autoReconnect(true)
                .socketOptions(SocketOptions.builder()
                        .connectTimeout(Duration.ofSeconds(3)) // 连接超时
                        .build())
                .build();
        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(redisLettuceProperties.getTimeout()))
                .clientOptions(clientOptions)
                //.clientResources(ClientResources.builder()
                //.reconnectDelay(Delay.exponential()) // 指数退避重连策略
                //.build())
                .build();
    }

    /**
     * 集群模式-客户端配置
     *
     * @return LettuceClientConfiguration
     */
    public LettuceClientConfiguration lettuceClientClusterConfiguration() {
        // 拓扑刷新配置（自动发现集群节点变化）
        ClusterTopologyRefreshOptions topologyRefreshOptions =
                ClusterTopologyRefreshOptions.builder()
                        .enablePeriodicRefresh(Duration.ofMinutes(redisLettuceProperties.getCluster().getPeriodicRefresh())) // 定期刷新
                        .enableAllAdaptiveRefreshTriggers() // 自适应刷新
                        .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(redisLettuceProperties.getCluster().getTriggersTimeout()))
                        .build();
        // 客户端选项
        ClientOptions clientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(topologyRefreshOptions)
                .autoReconnect(true)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .socketOptions(SocketOptions.builder()
                        .connectTimeout(Duration.ofSeconds(3)) // 连接超时
                        .keepAlive(true)
                        .build())
                .build();
        return LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(redisLettuceProperties.getTimeout()))
                .clientOptions(clientOptions)
                .build();
    }

    /**
     * lettuce工厂方法
     *
     * @return RedisConnectionFactory
     */
    @Bean(name = "lettuceConnectionFactory")
    public RedisConnectionFactory lettuceConnectionFactory() {
        if (StringUtils.hasLength(redisLettuceProperties.getHost())) {
            return new LettuceConnectionFactory(this.redisStandaloneConfiguration(),
                    this.lettuceClientStandaloneConfiguration());
        } else {
            return new LettuceConnectionFactory(this.redisClusterConfiguration(),
                    this.lettuceClientClusterConfiguration());
        }
    }

    /**
     * 1，用StringRedisSerializer进行序列化的值，在Java和Redis中保存的内容是一样的
     * <p>
     * 2，用Jackson2JsonRedisSerializer进行序列化的值，在Redis中保存的内容，比Java中多了一对双引号。
     * <p>
     * 3，用JdkSerializationRedisSerializer进行序列化的值，对于Key-Value的Value来说，是在Redis中是不可读的。对于Hash的Value来说，
     * 比Java的内容多了一些字符。
     *
     * @param redisConnectionFactory Redis Connection Factory
     * @return redisTemplate
     */
    @Bean
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 初始化布隆过滤器
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(name = {"com.google.common.hash.Hashing"})
    public RedisBloomFilter<String> redisBloomFilter() {
        return new RedisBloomFilter<>((Funnel<String>) (from, into) ->
                into.putString(from, Charsets.UTF_8).putString(from, Charsets.UTF_8),
                1000000,
                0.01);
    }
}
