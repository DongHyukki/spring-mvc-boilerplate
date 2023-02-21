package com.donghyukki.common.redis

enum class RedisAtomicSetResult {
    SUCCESS,
    FAIL,
    ;

    companion object {
        fun from(pTtl: Long): RedisAtomicSetResult {
            return when {
                pTtl <= 0 -> FAIL
                pTtl > 1 -> SUCCESS
                else -> throw IllegalStateException()
            }
        }
    }
}
