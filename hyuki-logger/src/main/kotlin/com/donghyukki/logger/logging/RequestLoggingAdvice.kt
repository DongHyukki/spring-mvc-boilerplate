package com.donghyukki.logger.logging

import com.donghyukki.logger.context.RequestContext
import com.donghyukki.logger.masking.MaskingObjectMapper
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Type

@ControllerAdvice
class RequestLoggingAdvice : RequestBodyAdviceAdapter() {

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Any {
        val bodyStr = MaskingObjectMapper.writeValueAsString(body)
        RequestContext.put("requestBody", bodyStr)
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType)
    }
}
