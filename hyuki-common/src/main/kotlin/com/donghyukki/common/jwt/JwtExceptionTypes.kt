package com.donghyukki.common.jwt

import com.donghyukki.common.exception.ExceptionType
import org.springframework.http.HttpStatus

enum class JwtExceptionTypes(
    override val status: HttpStatus,
    override val message: String,
    override val code: String? = null
) : ExceptionType {
    JWT_TOKEN_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "jwt token create fail"),
    JWT_TOKEN_VERIFY_FAIL(HttpStatus.BAD_REQUEST, "jwt token verify fail"),
    JWT_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "jwt token was expired")
    ;
}
