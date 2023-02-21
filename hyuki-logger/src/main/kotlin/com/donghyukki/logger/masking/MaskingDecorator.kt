package com.donghyukki.logger.masking

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import net.logstash.logback.decorate.JsonGeneratorDecorator

class MaskingDecorator : JsonGeneratorDecorator {

    override fun decorate(generator: JsonGenerator): JsonGenerator {
        val codec = generator.codec
        val mapper = codec as ObjectMapper
        mapper.setAnnotationIntrospector(MaskingIntroSpector())
        return generator
    }
}
