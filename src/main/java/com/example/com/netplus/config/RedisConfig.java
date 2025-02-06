package com.example.com.netplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
//    @Bean(name = "stringRedisTemplate")
//    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        StringRedisSerializer stringSerializer = new StringRedisSerializer();
//
//        template.setKeySerializer(stringSerializer);
//        template.setValueSerializer(stringSerializer);
//
//        template.setHashKeySerializer(stringSerializer);
//        template.setHashValueSerializer(stringSerializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }

    @Bean
    public RedisTemplate<String, Integer> integerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
