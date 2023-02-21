package com.donghyukki.common.redis

import com.donghyukki.common.exception.CustomExceptionType
import com.donghyukki.common.exception.GFRuntimeException
import com.donghyukki.common.extensions.trimNewLine
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.data.redis.connection.ReturnType
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(name = ["standaloneRedisConnectionFactory"])
class RedisAtomicTemplate(
    private val redisTemplate: RedisTemplate<String, Any>,
) {
    fun set(key: String, value: String, ttl: Long): RedisAtomicSetResult {
        val pTtl = redisTemplate
            .connectionFactory
            ?.connection
            ?.scriptingCommands()
            ?.eval<Long>(
                makeAtomicSetScript().toByteArray(),
                ReturnType.INTEGER,
                1,
                key.toByteArray(), StringRedisSerializer().serialize(value), ttl.toString().toByteArray()
            ) ?: GFRuntimeException(
            CustomExceptionType(
                status = HttpStatus.INTERNAL_SERVER_ERROR,
                message = "Redis Atomic Set Error",
                code = null
            )
        )

        return RedisAtomicSetResult.from(pTtl as Long)
    }

    /**
     * 성공         -> return ttl
     * 이미 key 존재 -> return 0
     * Set 실패     -> return -1
     */
    private fun makeAtomicSetScript(): String {
        return """
                |if (redis.call('exists', KEYS[1]) == 0)
                |    then
                |        redis.call('set', KEYS[1], ARGV[1]);
                |        redis.call('pexpire', KEYS[1], ARGV[2]);
                |else 
                |    return 0;
                |end; 
                |return redis.call('pttl', KEYS[1]);
                """.trimMargin().trimNewLine()
    }
}
