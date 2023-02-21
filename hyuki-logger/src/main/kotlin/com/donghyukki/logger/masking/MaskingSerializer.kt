package com.donghyukki.logger.masking

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class MaskingSerializer(
    private val maskValue: String
) : JsonSerializer<Any>() {
    override fun serialize(value: Any, gen: JsonGenerator, serializers: SerializerProvider?) {
        gen.writeString(maskValue)
    }
}
