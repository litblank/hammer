package com.exch.platform.config.plugin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: chenyadong
 * @Date: 2019/2/25 14:25
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

    @Value("${redis.sentinel.master:}")
    private String master;

    @Value("${redis.sentinel.nodes:127.0.0.1}")
    private String sentinelHost;

    @Value("${redis.sentinel.port:26380}")
    private Integer  sentinelPort;

    @Value("${redis.password:123123}")
    private String  password;

    /**
     * 哨兵配置
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.sentinel")
    public RedisSentinelConfiguration getRedisSentinelConfig(){
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
        if(StringUtils.isNotEmpty(master)){
            sentinelConfiguration.setMaster(master);
        }
        sentinelConfiguration.sentinel(sentinelHost,sentinelPort);
        return sentinelConfiguration;
    }

    @Bean
    @ConfigurationProperties(prefix="redis.pool")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix="redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory jedisFactory = new JedisConnectionFactory(getRedisSentinelConfig().getMaster()!=null?getRedisSentinelConfig():null,getRedisConfig());
        return jedisFactory;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> redisTemplate = new StringRedisTemplate(getConnectionFactory());
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        redisTemplate.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jacksonSeial);
        redisTemplate.afterPropertiesSet();

        //((RedisTemplate<String,Object>)redisTemplate).opsForValue().set("cyd0226","阿斯蒂芬请问asd123");
        return redisTemplate;
    }

}
