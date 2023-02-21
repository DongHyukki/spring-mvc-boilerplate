package com.donghyukki.logger.logging

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * - WebMvcConfigurer 에 Logging interceptor 등록
 * - 만약 WebMvcConfigurer 가 이미 정의 되어 있다면 해당 Config Class 에 LoggingInterceptor 추가
 */
@Configuration
@ConditionalOnMissingBean(WebMvcConfigurer::class)
class LoggingWebConfig(
    private val requestLoggingInterceptor: RequestLoggingInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(requestLoggingInterceptor)
    }
}
