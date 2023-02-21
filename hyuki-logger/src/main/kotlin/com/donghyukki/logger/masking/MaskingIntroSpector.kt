package com.donghyukki.logger.masking

import com.fasterxml.jackson.databind.introspect.Annotated
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector

class MaskingIntroSpector : NopAnnotationIntrospector() {

    override fun findSerializer(am: Annotated?): MaskingSerializer? {
        if (am == null) {
            return null
        }

        val ann = am.getAnnotation(MaskingData::class.java) ?: return null
        return MaskingSerializer(ann.value)
    }
}
