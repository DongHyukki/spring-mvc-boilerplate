package com.donghyukki.common.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@EnableCaching
@Configuration
class CacheConfig(
    @Value("\${spring.config.activate.on-profile:UNKNOWN_PHASE}")
    val phase: String,
) {

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeKeysWith(
                RedisSerializationContext
                    .SerializationPair
                    .fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext
                    .SerializationPair
                    .fromSerializer(GenericJackson2JsonRedisSerializer())
            )
    }

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
        return RedisCacheManagerBuilderCustomizer { builder ->
            run {
                CacheMetaHolder.values().forEach {
                    builder.withCacheConfiguration(
                        it.cacheName,
                        RedisCacheConfiguration
                            .defaultCacheConfig()
                            .prefixCacheNameWith("$phase::")
                            .entryTtl(Duration.ofSeconds(it.cacheTTL))
                    )
                }
            }
        }
    }
}
