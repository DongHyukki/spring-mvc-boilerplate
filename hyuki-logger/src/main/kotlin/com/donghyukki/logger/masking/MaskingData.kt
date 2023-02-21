package com.donghyukki.logger.masking

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class MaskingData(
    val value: String
)
