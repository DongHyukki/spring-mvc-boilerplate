package com.donghyukki.common.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.donghyukki.common.exception.HyukiRuntimeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JwtTokenManager(
    @Value("\${token.secret:default-secret}") private val secret: String
) {

    companion object {
        const val ISSUER = "com.donghyukki"
    }

    fun createToken(subject: String?, expireAt: Instant): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)

            return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expireAt)
                .withIssuer(ISSUER)
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_CREATE_FAIL)
        }
    }

    fun createTokenWithClaim(subject: String?, claims: Map<String, String>, expireAt: Instant): String {
        try {
            val algorithm = Algorithm.HMAC256(secret)

            val jwtBuilder = JWT.create()
                .withSubject(subject)
                .withExpiresAt(expireAt)
                .withIssuer(ISSUER)

            claims.entries.forEach {
                jwtBuilder.withClaim(it.key, it.value)
            }

            return jwtBuilder.sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_CREATE_FAIL)
        }
    }

    fun verifyToken(token: String) {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm).build()
            verifier.verify(token)
        } catch (exception: TokenExpiredException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_EXPIRED)
        } catch (exception: JWTVerificationException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_VERIFY_FAIL)
        }
    }

    fun getIssuerAndSubject(token: String): Pair<String, String> {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm).build()
            val decodedJWT = verifier.verify(token)

            return Pair(decodedJWT.issuer, decodedJWT.subject)
        } catch (exception: TokenExpiredException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_EXPIRED)
        } catch (exception: JWTVerificationException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_VERIFY_FAIL)
        }
    }

    fun getSubjectAndClaims(token: String): Pair<String, Map<String, String>> {
        try {
            val algorithm = Algorithm.HMAC256(secret)
            val verifier = JWT.require(algorithm).build()
            val decodedJWT = verifier.verify(token)
            val claims: MutableMap<String, String> = mutableMapOf()

            decodedJWT.claims.entries.forEach {
                claims[it.key] = it.value.asString()
            }

            return Pair(decodedJWT.issuer, claims)
        } catch (exception: TokenExpiredException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_EXPIRED)
        } catch (exception: JWTVerificationException) {
            throw HyukiRuntimeException(JwtExceptionTypes.JWT_TOKEN_VERIFY_FAIL)
        }
    }
}
