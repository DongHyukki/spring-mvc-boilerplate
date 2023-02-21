package com.donghyukki.common.redis

import com.donghyukki.common.context.ProfileContext
import org.springframework.data.redis.listener.PatternTopic
import org.springframework.data.redis.listener.Topic

enum class RedisTopics(
    val topic: Topic,
) {
    CUSTOM_EXPIRED_EVENT(
        topic = PatternTopic(getRedisEventKeyPattern(EXPIRE_KEY_INFIX)),
    ),
    ;

    companion object {
        fun toMutableCollection() = values()
            .map { it.topic }
            .toMutableList()

        fun fromPatternBytes(pattern: ByteArray): RedisTopics {
            return values().first { it.topic.toString() == String(pattern) }
        }
    }
}

private fun getRedisEventKeyPattern(keyInfix: String): String {
    return "__keyspace@0__:${ProfileContext.phase}$keyInfix*"
}

private const val EXPIRE_KEY_INFIX = ":custom:expire:"
