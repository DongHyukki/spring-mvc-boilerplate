package com.donghyukki.common.extensions

import org.apache.commons.lang3.StringUtils
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

inline fun <T> Collection<T>.isNotEmptyThen(f: () -> Unit) {
    if (this.isNotEmpty()) {
        f()
    }
}

fun <String, V> multiValueMapOf(vararg elements: Pair<String, V>): LinkedMultiValueMap<kotlin.String, V> {
    val map = LinkedMultiValueMap<kotlin.String, V>()
    elements.forEach {
        map.add(it.first.toString(), it.second)
    }

    return map
}

fun <String, V> multiValueMapOf(): LinkedMultiValueMap<String, V> {
    return LinkedMultiValueMap<String, V>()
}

fun String.trimNewLine(): String {
    return this.replace("\n", "")
}

fun String.trimHyphen(): String {
    return this.replace("-", "")
}

fun HttpStatus.toCode(): String {
    return this.value().toString()
}

fun LocalDateTime.atEndOfDay(): LocalDateTime {
    // LocalTime.MAX = 23:59:59.999999999, micro 단위까지 표현하기 위해 minus 추가 => 23:59:59.999999
    return this.toLocalDate().atTime(LocalTime.MAX).minusNanos(1000)
}

fun LocalDateTime.toOnlyDate(): LocalDateTime {
    return this.toLocalDate().atStartOfDay()
}

fun Pageable.withSort(sort: Sort): PageRequest {
    return PageRequest.of(this.pageNumber, this.pageSize, sort)
}

fun String?.toLocalDateTime(): LocalDateTime {
    val timeDelimiter = "T"
    val timePadding = "T00:00:00"

    if (this.isNullOrBlank()) {
        return LocalDateTime.now()
    }

    if (this.contains(timeDelimiter, ignoreCase = true).not()) {
        return LocalDateTime.parse("${this}$timePadding", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    return LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun List<String>.isNoneEmpty(): Boolean {
    return StringUtils.isNoneEmpty(*this.toTypedArray())
}
