package com.donghyukki.common.concurrency

/**
 * 멱등성을 보장하기 위한 Annotation
 * Controller Class 의 fun 에 설정한다.
 *
 * 주의사항
 * 메소드 파라미터로 @RequestHeader headers: Map<String, String> 설정
 * 리턴 타입은 Any 로 설정
 * Default LockReleaseTtl = 5초
 * Default CacheTtl = 하루
 * [ example ]
 *
 * @Idempotency(
 *  lockReleaseTtl = 3000L
 *  cacheTtl = 4000L
 * )
 * @PostMapping("/test")
 * fun testIdempotency(@RequestHeader headers: Map<String, String>, @RequestBody body: TestBody): Any { }
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Idempotency(
    val lockReleaseTtl: Long = 5000L, // ( 5초 )
    val cacheTtl: Long = 60 * 24, // 60분 * 24시간 ( 하루 )
)

const val IDEMPOTENCY_KEY = "Idempotency-Key"
const val API_KEY = "API-KEY"

data class IdempotencyHeaders(
    val apiKey: String?,
    val idempotency: String,
) {
    companion object {
        fun from(headers: List<Any>): IdempotencyHeaders {
            val headerMap = headers.map { it as LinkedHashMap<*, *> }.first {
                it.containsKey(IDEMPOTENCY_KEY)
            }

            return IdempotencyHeaders(
                apiKey = headerMap[API_KEY]?.let { it as String } ?: "",
                idempotency = headerMap[IDEMPOTENCY_KEY] as String
            )
        }
    }
}

data class CachedResponse(
    val body: CachedResponseBody,
    val status: String,
    val statusCode: String,
    val statusCodeValue: Int,
)

data class CachedResponseBody(
    val code: String,
    val data: Any
)
