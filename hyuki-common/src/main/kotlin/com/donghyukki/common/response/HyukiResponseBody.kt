package com.donghyukki.common.response

import com.donghyukki.common.extensions.toCode
import org.springframework.http.HttpStatus

class HyukiResponseBody<T>(
    val code: String,
    val data: T? = null
) {
    constructor() : this(HttpStatus.OK.toCode(), null)
    constructor(data: T) : this(HttpStatus.OK.toCode(), data)
}
