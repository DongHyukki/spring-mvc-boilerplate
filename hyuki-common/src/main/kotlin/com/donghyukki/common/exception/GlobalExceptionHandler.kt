package com.donghyukki.common.exception

import com.donghyukki.common.response.HyukiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(HyukiRuntimeException::class)
    fun handleAppException(ex: HyukiRuntimeException): HyukiResponse<String> {
        logger.info(ex.message)

        return HyukiResponse.fail(
            body = ex.getType().message,
            httpStatus = ex.getType().status,
            code = ex.getType().code
        )
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(ex: NoHandlerFoundException): HyukiResponse<String> {
        logger.error(ex.stackTraceToString())

        return HyukiResponse.fail(
            body = HttpStatus.NOT_FOUND.reasonPhrase,
            httpStatus = HttpStatus.NOT_FOUND,
            code = null
        )
    }

    @ExceptionHandler
    fun handleConstraintViolationException(ex: ConstraintViolationException): HyukiResponse<String> {
        logger.debug(ex.message)

        val message = ex.constraintViolations.joinToString { it.message }
        return HyukiResponse.fail(
            body = message,
            httpStatus = HttpStatus.BAD_REQUEST,
            code = null
        )
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): HyukiResponse<String> {
        logger.debug(ex.message)

        val message = ex.bindingResult.allErrors.mapNotNull { it.defaultMessage }.joinToString()
        return HyukiResponse.fail(
            body = message,
            httpStatus = HttpStatus.BAD_REQUEST,
            code = null
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectException(ex: Exception): HyukiResponse<String> {
        logger.error(ex.stackTraceToString())

        return HyukiResponse.fail(
            body = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            code = null
        )
    }
}
