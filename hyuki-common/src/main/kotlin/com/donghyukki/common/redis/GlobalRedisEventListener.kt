package com.donghyukki.common.redis

import com.donghyukki.common.event.EventPublisher
import com.donghyukki.common.transaction.TransactionInvoker
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component

@Component
@ConditionalOnRedisProperty
class GlobalRedisEventListener(
    private val eventPublisher: EventPublisher,
    private val transactionInvoker: TransactionInvoker
) : MessageListener {

    companion object {
        private const val VALUE_ID_INDEX = 4
        private const val VALUE_KEY_DELIMITER = ":"
    }

    override fun onMessage(message: Message, pattern: ByteArray?) {
        pattern?.let {
            when (RedisTopics.fromPatternBytes(pattern)) {
                RedisTopics.CUSTOM_EXPIRED_EVENT -> {
                    // val value = parseValue(String(message.channel))

                    transactionInvoker.invoke {
                        // eventPublisher.publishEvent()
                    }
                }
            }
        }
    }

    // private fun parseValue(expiredKey: String): String {
    //     return expiredKey.split(VALUE_KEY_DELIMITER)[VALUE_ID_INDEX]
    // }
}
