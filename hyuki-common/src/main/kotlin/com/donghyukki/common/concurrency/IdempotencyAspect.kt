package com.donghyukki.common.concurrency

import com.donghyukki.common.extensions.toCode
import com.donghyukki.common.jackson.HyukiJsonUtil
import com.donghyukki.common.response.HyukiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.Objects
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

@Component
@Aspect
@ConditionalOnBean(name = ["standaloneRedisConnectionFactory"])
class IdempotencyAspect(
    private val concurrencyManager: ConcurrencyManager,
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {

    @Around("@annotation(com.donghyukki.common.concurrency.Idempotency)")
    fun processIdempotency(proceedingJoinPoint: ProceedingJoinPoint): Any {

        val parameters = proceedingJoinPoint.args
        val (headers, bodyAndParams) = splitArgs(parameters)
        // 멱등 키가 있는가?
        if (containsIdempotencyKey(headers).not()) {
            return proceedingJoinPoint.proceed()
        }
        // 멱등 처리 할 Key 생성
        val methodSignature = proceedingJoinPoint.signature as MethodSignature
        val idempotencyHeaders = IdempotencyHeaders.from(headers)
        val methodName = methodSignature.name
        val primaryValue = generatePrimaryValue(idempotencyHeaders, bodyAndParams, methodName)
        val key = "$primaryValue-key"
        val valueKey = "$primaryValue-value"

        // 캐싱된 값이 있으면 바로 반환한다.
        val cachedValue = redisTemplate.opsForValue().get(valueKey)
        cachedValue?.let {
            val cachedResponse = HyukiJsonUtil.toObject<CachedResponse>(cachedValue.toString())
            if (cachedResponse.body.code == HttpStatus.OK.toCode()) {
                return cachedResponse.body
            }

            return HyukiResponse.fail(
                body = cachedResponse.body,
                httpStatus = HttpStatus.valueOf(cachedResponse.statusCodeValue),
                code = cachedResponse.body.code
            )
        }

        // ttl 가져오기
        val annotationValues: Idempotency = methodSignature.method.getAnnotation(Idempotency::class.java)
        val lockReleaseTtl = annotationValues.lockReleaseTtl
        val cacheTtl = annotationValues.cacheTtl

        // Lock 을 걸고 컨트롤러를 실행 시킨다.
        val (success, result) = concurrencyManager.lockAndInvoke(key, lockReleaseTtl) { proceedingJoinPoint.proceed() }
        if (success) {
            // 실행된 값을 캐싱하고 응답을 준다.
            redisTemplate.opsForValue().set(
                valueKey,
                objectMapper.writeValueAsString(result),
                cacheTtl.toDuration(DurationUnit.MINUTES).toJavaDuration()
            )
            return result!!
        }

        // Lock 획득에 실패 ( 다른 쓰레드 혹은 어플리케이션에서 처리중... )
        return HyukiResponse.fail("IDEMPOTENT_REQUEST_PROCESSING", HttpStatus.CONFLICT, null)
    }

    private fun splitArgs(parameters: Array<Any>): Pair<List<Any>, List<Any>> {
        return parameters.asList().partition { it is LinkedHashMap<*, *> }
    }

    private fun containsIdempotencyKey(headers: List<Any>): Boolean {
        return headers
            .map { it as LinkedHashMap<*, *> }
            .any {
                it.containsKey(IDEMPOTENCY_KEY)
            }
    }

    private fun generatePrimaryValue(
        idempotencyHeaders: IdempotencyHeaders,
        bodyAndParams: List<Any>,
        methodName: String
    ): String {
        val sb = StringBuilder()
        sb.append(Objects.hashCode(idempotencyHeaders))
        sb.append(Objects.hashCode(methodName))

        bodyAndParams.forEach {
            sb.append(Objects.hashCode(it))
        }

        return sb.toString()
    }
}
