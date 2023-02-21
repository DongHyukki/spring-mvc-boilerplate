package com.donghyukki.app.config

import com.donghyukki.common.exception.GlobalExceptionHandler
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@Profile("test")
@Component
class E2ETestMockMvc(
    private val webApplicationContext: WebApplicationContext
) {

    fun <T> getStandaloneMockMvc(clazz: Class<T>): MockMvc {
        return MockMvcBuilders.standaloneSetup(clazz).setControllerAdvice(GlobalExceptionHandler()).build()
    }

    fun getSpringContextMockMvc(): MockMvc {
        return MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }
}
