package com.donghyukki.common.async

import com.donghyukki.common.exception.HyukiRuntimeException
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import java.lang.reflect.Method

class SpringAsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun handleUncaughtException(e: Throwable, method: Method, vararg params: Any?) {
        if (e is HyukiRuntimeException) {
            log.error("Async Exception : {}", e.getType(), e)
        } else {
            log.error("Async Exception : {}", e.message, e)
        }
    }
}
