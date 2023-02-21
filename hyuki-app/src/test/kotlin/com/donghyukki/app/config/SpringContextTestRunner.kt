package com.donghyukki.app.config

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [SpringContextConfigurer::class],
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
interface SpringContextTestRunner
