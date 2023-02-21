package com.donghyukki.common.transaction

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionInvoker {
    @Transactional
    fun <T> invoke(
        function: () -> T
    ): T {
        return function.invoke()
    }
}
