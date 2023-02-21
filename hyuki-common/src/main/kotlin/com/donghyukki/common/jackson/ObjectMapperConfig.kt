package com.donghyukki.common.jackson

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonMapperBuilder()
            .addModule(
                JavaTimeModule()
                    .addSerializer(
                        LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )
                    .addSerializer(
                        LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE)
                    )
            )
            .build()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
}
