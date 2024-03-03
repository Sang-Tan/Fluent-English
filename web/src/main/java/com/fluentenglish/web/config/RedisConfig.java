package com.fluentenglish.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> stringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Integer> stringIntegerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RedisSerializer<Integer>() {
            @Override
            public byte[] serialize(Integer integer) throws SerializationException {
                if (integer == null) {
                    return null;
                }

                int value = integer;
                return new byte[]{
                        (byte) (value >> 24),
                        (byte) (value >> 16),
                        (byte) (value >> 8),
                        (byte) (value)
                };
            }

            @Override
            public Integer deserialize(byte[] bytes) throws SerializationException {
                if (bytes == null) {
                    return null;
                }

                if (bytes.length != 4) {
                    throw new IllegalArgumentException("Byte array must be of length 4");
                }
                return (bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | (bytes[3]);
            }
        });

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
