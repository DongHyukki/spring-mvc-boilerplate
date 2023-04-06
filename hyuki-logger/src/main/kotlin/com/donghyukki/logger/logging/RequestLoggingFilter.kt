package com.donghyukki.logger.logging

import com.donghyukki.logger.context.RequestContext
import com.donghyukki.logger.masking.MaskingObjectMapper
import net.logstash.logback.argument.StructuredArguments
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLoggingFilter : OncePerRequestFilter() {

    private val accessLogger = LoggerFactory.getLogger("ACCESS_LOGGER")

    override fun doFilterInternal(
        originalRequest: HttpServletRequest,
        originalResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val request = ContentCachingRequestWrapper(originalRequest)
        val response = ContentCachingResponseWrapper(originalResponse)
        val requestAt = LocalDateTime.now()

        try {
            RequestContext.initContext("ACCESS")
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            RequestContext.putException(ex)
        } finally {
            val now = LocalDateTime.now()
            RequestContext.put("request", extractRequestLog(request, requestAt))
            RequestContext.put("response", extractResponseLog(response, now))
            RequestContext.put("duration", requestAt.until(now, ChronoUnit.MILLIS))

            accessLogger.info("", StructuredArguments.value("context", RequestContext.toLog()))

            response.
            copyBodyToResponse()
            RequestContext.clearContext()
        }
    }

    private fun extractRequestLog(request: ContentCachingRequestWrapper, requestAt: LocalDateTime): Map<String, Any?> {
        val log = mutableMapOf<String, Any?>()
        log["url"] = request.requestURI
        log["queryString"] = request.queryString
        log["method"] = request.method

        val headers = mutableMapOf<String, String>()
        request.headerNames.toList()
            .forEach {
                headers[it] = headers[it] ?: ("${request.getHeader(it)}\n")
            }

        log["remote-addr"] = request.remoteAddr
        log["headers"] = headers
        log["parameters"] = request.parameterMap
        log["body"] = RequestContext.get("requestBody")
        log["bodySize"] = request.contentLength
        log["createAt"] = requestAt.toString()

        RequestContext.remove("requestBody")

        return log
    }

    private fun extractResponseLog(
        response: ContentCachingResponseWrapper,
        responseAt: LocalDateTime
    ): Map<String, Any?> {
        val log = mutableMapOf<String, Any?>()

        val headers = mutableMapOf<String, String>()
        response.headerNames.toList()
            .forEach {
                headers[it] = headers[it] ?: ("${response.getHeader(it)}\n")
            }

        log["status"] = response.status
        log["headers"] = MaskingObjectMapper.writeValueAsString(headers)
        log["body"] = RequestContext.get("responseBody")
        log["bodySize"] = response.contentSize
        log["createAt"] = responseAt.toString()

        RequestContext.remove("responseBody")

        return log
    }
}
