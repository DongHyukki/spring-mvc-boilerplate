package com.donghyukki.app.config

import io.mockk.mockk
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.sleuth.Tracer
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["com.donghyukki"])
class SpringContextConfigurer {

    @Bean
    fun tracer(): Tracer = mockk(relaxed = true)
}
