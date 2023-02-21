package com.donghyukki.app.utils

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import java.time.LocalDateTime
import kotlin.math.roundToInt
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

inline fun <reified T> changePrivateField(instance: T, fieldName: String, changeValue: Any) {
    val field = instance!!::class.memberProperties.first { it.name == fieldName }
    field as KMutableProperty<*>
    field.setter.call(instance, changeValue)
}

fun <T> changePrivateValField(instance: T, fieldName: String, changeValue: Any) {
    val declaredFields = instance!!::class.java.declaredFields
    val findField = declaredFields.toList().first { it.name == fieldName }
    if (findField.trySetAccessible()) {
        findField.set(instance, changeValue)
    }
}

fun haveSameMicroSecond(date: LocalDateTime): Matcher<LocalDateTime> = object : Matcher<LocalDateTime> {
    // DB의 datetime(6)는 microsecond 아래자리에서 반올림 된다고 가정(추측)한다.
    override fun test(value: LocalDateTime): MatcherResult {
        val valueDate = value.toLocalDate()
        val valueMicroSecond = (value.nano.toDouble() / 1_000).roundToInt()
        val dateDate = date.toLocalDate()
        val dateMicroSecond = (date.nano.toDouble() / 1_000).roundToInt()

        return MatcherResult(
            valueDate == dateDate && valueMicroSecond == dateMicroSecond,
            { "$value should have micro-seconds $dateDate.$dateMicroSecond but had $valueDate.$valueMicroSecond" },
            { "$value should not have day $dateDate.$dateMicroSecond" }
        )
    }
}

infix fun LocalDateTime.shouldHaveSaveMicroSecondAs(date: LocalDateTime) =
    this should haveSameMicroSecond(date)
