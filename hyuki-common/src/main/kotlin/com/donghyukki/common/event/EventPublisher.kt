package com.donghyukki.common.event

interface EventPublisher {
    fun publishEvent(event: EventDTO)
}
