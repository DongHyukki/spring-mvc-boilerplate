package com.donghyukki.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import java.io.IOException
import java.net.Socket
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * 로컬 테스트 환경의 경우 Embedded Redis 를 활용한다.
 * CI 환경의 경우 OS에 따라 Embedded Redis 에 이슈가 있기 때문에 CI build Step 에 Redis 설치를 포함시킨다.
 */
@OnlyLocalTest
@Configuration
class EmbeddedRedisConfig {
    @Value("\${spring.redis.port}")
    lateinit var port: String

    private var redisServer: RedisServer? = null

    @PostConstruct
    @Throws(IOException::class)
    fun redisServer() {
        redisServer = RedisServer(Integer.parseInt(port))

        if (isPortOpen(port)) {
            redisServer!!.start()
        }
    }

    @PreDestroy
    fun stopRedis() {
        redisServer?.stop()
    }

    private fun isPortOpen(port: String): Boolean {
        return try {
            Socket("localhost", port.toInt())
            false
        } catch (e: IOException) {
            true
        }
    }
}
