package com.donghyukki.common.async

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class SpringAsyncTaskConfig : AsyncConfigurer {

    companion object {
        const val EVENT_THREAD_NAME_PREFIX = "async-event-executor"
    }

    override fun getAsyncExecutor(): Executor? {
        val coreCount = Runtime.getRuntime()
            .availableProcessors()
            .coerceAtLeast(5)

        return ThreadPoolTaskExecutor()
            .apply {
                corePoolSize = (coreCount * 2)
                maxPoolSize = (coreCount * 10)
                queueCapacity = 10000
                setWaitForTasksToCompleteOnShutdown(true)
                setAwaitTerminationSeconds(10)
                initialize()
            }
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return SpringAsyncExceptionHandler()
    }

    // @Bean
    // fun asyncEventExecutor(): ThreadPoolTaskExecutor {
    //     val coreCount = Runtime.getRuntime()
    //         .availableProcessors()
    //         .coerceAtLeast(5)
    //
    //     return ThreadPoolTaskExecutor().apply {
    //         this.corePoolSize = (coreCount * 2)
    //         this.maxPoolSize = (coreCount * 10)
    //         queueCapacity = (coreCount * 20)
    //         setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
    //         setWaitForTasksToCompleteOnShutdown(true)
    //         setThreadNamePrefix(Companion.EVENT_THREAD_NAME_PREFIX)
    //         initialize()
    //     }
    // }
}
