package com.donghyukki.logger.logging

import com.donghyukki.logger.context.RequestContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestLoggingInterceptor(
    @Value("\${spring.config.activate.on-profile:UNKNOWN_PHASE}")
    val phase: String,
    @Autowired private val tracer: Tracer
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        RequestContext.put("phase", phase)
        val method = handler as? HandlerMethod
        val controller = mutableMapOf<String, Any?>()

        controller["mapping"] = method?.getMethodAnnotation(RequestMapping::class.java)?.value?.joinToString("\n")
        controller["method"] = method?.method?.name
        controller["class"] = method?.beanType?.name

        println("RequestLoggingInterceptor!!!!!!!!")

        RequestContext.put("controller", controller)

        return super.preHandle(request, response, handler)
    }
}
