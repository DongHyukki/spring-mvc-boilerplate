package com.donghyukki.common.redis

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@ConditionalOnProperty(prefix = "spring", name = ["redis.host", "redis.port"])
annotation class ConditionalOnRedisProperty
