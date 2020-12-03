package com.dataexa.config;

import com.dataexa.constant.RedisConstant;
import com.dataexa.constant.SymbolConstants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 胡志成
 * @date 2020/6/8
 */
@Lazy
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeOut;
    @Value("${spring.redis.model}")
    private int model;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.maxRedirects}")
    private int maxRedirects;
    @Value("${spring.redis.jedis.pool.blockWhenExhausted}")
    private boolean blockWhenExhausted;
    @Value("${spring.redis.jedis.pool.maxActive}")
    private int maxTotal;
    @Value("${spring.redis.jedis.pool.maxIdle}")
    private int maxIdle;
    @Value("${spring.redis.jedis.pool.minIdle}")
    private int minIdle;
    @Value("${spring.redis.jedis.pool.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${spring.redis.jedis.pool.numTestsPerEvictionRun}")
    private int numTestsPerEvictionRun;
    @Value("${spring.redis.jedis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.redis.jedis.pool.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.redis.jedis.pool.softMinEvictableIdleTimeMillis}")
    private int softMinEvictableIdleTimeMillis;

    @Bean(name = "jedisPoolConfig")
    JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig pool = new JedisPoolConfig();
        pool.setMaxIdle(maxIdle);
        pool.setMinIdle(minIdle);
        pool.setBlockWhenExhausted(blockWhenExhausted);
        pool.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        pool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        pool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        pool.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
        pool.setMaxTotal(maxTotal);
        pool.setMaxWaitMillis(maxWaitMillis);
        return pool;
    }

    @Bean(name = "redisClusterConfiguration")
    public RedisClusterConfiguration getRedisClusterConfiguration() {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodes = new HashSet<>();

        String[] nodeArr = clusterNodes.split(SymbolConstants.COMMA);
        for (String s : nodeArr) {
            String hostName = s.split(SymbolConstants.COLON)[0];
            int port = Integer.parseInt(s.split(SymbolConstants.COLON)[1]);
            RedisNode node = new RedisNode(hostName, port);
            nodes.add(node);
        }
        clusterConfig.setClusterNodes(nodes);
        clusterConfig.setMaxRedirects(maxRedirects);
        clusterConfig.setPassword(RedisPassword.of(""));
        return clusterConfig;
    }

    @Bean(name = "redisStandaloneConfiguration")
    public RedisStandaloneConfiguration getRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(hostName);
        standaloneConfig.setPort(port);
        standaloneConfig.setPassword("");
        return standaloneConfig;
    }



    @Bean(name = "jedisConnectionFactory")
    JedisConnectionFactory jedisConnectionFactory(
            @Qualifier("redisClusterConfiguration") RedisClusterConfiguration clusterConfig,
            @Qualifier("redisStandaloneConfiguration") RedisStandaloneConfiguration standaloneConfig,
            @Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory jedisConnectionFactory = null;
        //单机模式
        if(model == RedisConstant.STANDALONE){
            JedisClientConfiguration.JedisPoolingClientConfigurationBuilder builder =
                    (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
            builder.poolConfig(jedisPoolConfig);
            JedisClientConfiguration jedisClientConfiguration = builder.build();
            jedisConnectionFactory = new JedisConnectionFactory(standaloneConfig, jedisClientConfiguration);
            jedisConnectionFactory.afterPropertiesSet();
        } else if(model == RedisConstant.SENTINEL){

            //哨兵模式
        } else if(model == RedisConstant.CLUSTER){
            //Cluster模式
            jedisConnectionFactory = new JedisConnectionFactory(clusterConfig, jedisPoolConfig);
        }
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, Object> getRedisTemplate(
            @Qualifier("jedisConnectionFactory") JedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        @SuppressWarnings({ "rawtypes", "unchecked" })
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
