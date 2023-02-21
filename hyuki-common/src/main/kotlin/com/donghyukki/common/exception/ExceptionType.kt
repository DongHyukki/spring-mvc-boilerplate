package com.donghyukki.common.exception

import org.springframework.http.HttpStatus

interface ExceptionType {
    val status: HttpStatus
    val message: String
    val code: String?
    var enableAlert: Boolean?
}

class CustomExceptionType(
    override val status: HttpStatus,
    override val message: String,
    override val code: String?,
    override var enableAlert: Boolean? = true
) : ExceptionType
