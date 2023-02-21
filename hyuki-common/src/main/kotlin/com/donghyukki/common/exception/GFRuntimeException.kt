package com.donghyukki.common.exception

class GFRuntimeException(
    private val type: ExceptionType,
) : RuntimeException(type.message) {
    fun getType() = type
    fun getEnableAlert() = type.enableAlert
}
