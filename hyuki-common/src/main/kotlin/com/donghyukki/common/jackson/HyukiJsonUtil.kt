package com.donghyukki.common.jackson

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import java.time.format.DateTimeFormatter

object HyukiJsonUtil {

    val objectMapper: ObjectMapper = jacksonMapperBuilder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .addModule(
            JavaTimeModule()
                .addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .addSerializer(LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
        )
        .build()

    inline fun <reified T> toObject(str: String): T {
        return objectMapper.readValue(str, object : TypeReference<T>() {})
    }

    fun toJsonString(obj: Any): String {
        return objectMapper.writeValueAsString(obj)
    }

    inline fun <reified T> toObject(obj: Any): T = toObject(toJsonString(obj))
}
