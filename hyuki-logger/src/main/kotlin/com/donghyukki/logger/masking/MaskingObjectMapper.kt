package com.donghyukki.logger.masking

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import java.time.format.DateTimeFormatter

object MaskingObjectMapper {
    private val objectMapper = jacksonMapperBuilder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .addModule(
            JavaTimeModule()
                .addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .addSerializer(LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        )
        .annotationIntrospector(MaskingIntroSpector())
        .build()

    fun writeValueAsString(value: Any?): String {
        value?.let {
            return objectMapper.writeValueAsString(value)
        } ?: return ""
    }
}
