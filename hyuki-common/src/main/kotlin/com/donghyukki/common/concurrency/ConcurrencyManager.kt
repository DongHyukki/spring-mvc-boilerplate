package com.donghyukki.common.concurrency

import com.donghyukki.common.exception.CustomExceptionType
import com.donghyukki.common.exception.GFRuntimeException
import com.donghyukki.common.redis.RedisAtomicSetResult
import com.donghyukki.common.redis.RedisAtomicTemplate
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.http.HttpStatus
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(name = ["standaloneRedisConnectionFactory"])
class ConcurrencyManager(
    private val atomicTemplate: RedisAtomicTemplate
) {
    companion object {
        const val LOCK_VALUE = "locked"
        const val DEFAULT_TTL = 2000L
    }

    fun <T> lockAndInvoke(
        key: String,
        ttl: Long?,
        function: () -> T
    ): Pair<Boolean, T?> {
        return when (atomicTemplate.set(key, LOCK_VALUE, ttl ?: DEFAULT_TTL)) {
            RedisAtomicSetResult.SUCCESS -> {
                Pair(true, function.invoke())
            }

            RedisAtomicSetResult.FAIL -> {
                Pair(false, null)
            }
        }
    }

    fun <T> retryWhenOptimisticLockFail(
        function: () -> T
    ): T? {
        val maxAttemptCount = 2
        var attemptCount = 0
        for (i in 0..maxAttemptCount) {
            try {
                return function.invoke()
            } catch (ex: ObjectOptimisticLockingFailureException) {
                attemptCount++
                if (attemptCount == maxAttemptCount) {
                    throw GFRuntimeException(
                        CustomExceptionType(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Exceed Max Attempt Count Optimistic Lock Retry",
                            null
                        )
                    )
                }
            }
        }

        return null
    }
}
