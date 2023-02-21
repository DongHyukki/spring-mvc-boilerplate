package com.donghyukki.logger.tracing

import com.donghyukki.logger.context.RequestContext
import org.springframework.cloud.sleuth.Tracer
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@DependsOn(value = ["RequestLoggingFilter"])
@Component
class TracingFilter(
    private val tracer: Tracer
) : OncePerRequestFilter() {

    companion object {
        const val TRACE_ID_HEADER_NAME = "HYUKI-TRACE-ID"
        const val TRACE_ID_KEY = "HYUKI-TRACE-ID"
        const val SPAN_ID_KEY = "HYUKI-SPAN-ID"
        const val PARENT_ID_KEY = "HYUKI-PARENT-SPAN-ID"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val currentSpan = tracer.currentSpan()
        if (currentSpan == null) {
            filterChain.doFilter(request, response)
            return
        }

        RequestContext.put(SPAN_ID_KEY, tracer.currentSpan()?.context()?.spanId() ?: "")
        RequestContext.put(TRACE_ID_KEY, tracer.currentSpan()?.context()?.traceId() ?: "")
        RequestContext.put(PARENT_ID_KEY, tracer.currentSpan()?.context()?.parentId() ?: "")

        (response as? HttpServletResponse)?.addHeader(
            TRACE_ID_HEADER_NAME,
            currentSpan.context().traceId()
        )

        filterChain.doFilter(request, response)
    }
}
