package com.donghyukki.logger.logging

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LoggingFilterConfig {

    @Bean("RequestLoggingFilter")
    fun requestLoggingFilter(): FilterRegistrationBean<RequestLoggingFilter> {
        val filterRegistrationBeanRequest = FilterRegistrationBean<RequestLoggingFilter>(
            RequestLoggingFilter()
        )
        filterRegistrationBeanRequest.setName("RequestLoggingFilter")
        return filterRegistrationBeanRequest
    }

    @Bean("ResponseEncodingFilter")
    fun responseEncodingFilter(): FilterRegistrationBean<ResponseEncodingFilter> {
        val filterRegistrationBeanRequest = FilterRegistrationBean<ResponseEncodingFilter>(
            ResponseEncodingFilter()
        )
        filterRegistrationBeanRequest.setName("ResponseEncodingFilter")
        return filterRegistrationBeanRequest
    }
}
