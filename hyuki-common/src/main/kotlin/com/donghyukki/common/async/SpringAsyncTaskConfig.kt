package com.donghyukki.common.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.ThreadPoolExecutor

@Configuration
@EnableAsync
class SpringAsyncTaskConfig {

    companion object {
        const val EVENT_THREAD_NAME_PREFIX = "async-event-executor"
    }

    @Bean
    fun asyncEventExecutor(): ThreadPoolTaskExecutor {
        val coreCount = Runtime.getRuntime()
            .availableProcessors()
            .coerceAtLeast(5)

        return ThreadPoolTaskExecutor().apply {
            this.corePoolSize = (coreCount * 2)
            this.maxPoolSize = (coreCount * 10)
            queueCapacity = (coreCount * 20)
            setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
            setWaitForTasksToCompleteOnShutdown(true)
            setThreadNamePrefix(Companion.EVENT_THREAD_NAME_PREFIX)
            initialize()
        }
    }
}
