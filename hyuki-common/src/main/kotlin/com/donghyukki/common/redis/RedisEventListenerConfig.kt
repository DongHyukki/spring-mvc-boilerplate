package com.donghyukki.common.redis

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.listener.RedisMessageListenerContainer

@Configuration
class RedisEventListenerConfig {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    @Primary
    fun redisMessageListenerContainer(
        redisConnectionFactory: RedisConnectionFactory,
        globalRedisEventListener: GlobalRedisEventListener
    ): RedisMessageListenerContainer {
        val redisMessageListenerContainer = RedisMessageListenerContainer()
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory)
        redisMessageListenerContainer.addMessageListener(globalRedisEventListener, RedisTopics.toMutableCollection())
        redisMessageListenerContainer.setErrorHandler { e ->
            logger.error("redis message listener error %S", e.stackTraceToString())
        }
        return redisMessageListenerContainer
    }
}
