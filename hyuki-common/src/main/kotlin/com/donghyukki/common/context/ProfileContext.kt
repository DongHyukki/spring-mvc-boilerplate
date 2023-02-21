package com.donghyukki.common.context

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ProfileContext(
    @Value("\${spring.config.activate.on-profile:UNKNOWN_PHASE}")
    private val phase: String
) {

    companion object {
        lateinit var phase: String
    }

    init {
        Companion.phase = phase
    }
}
