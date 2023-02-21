package com.donghyukki.common.exception

class HyukiRuntimeException(
    private val type: ExceptionType,
) : RuntimeException(type.message) {
    fun getType() = type
}
