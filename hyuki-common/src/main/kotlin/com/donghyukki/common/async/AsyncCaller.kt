package com.donghyukki.common.async

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class AsyncCaller {

    @Async
    fun callAsync(f: () -> Unit) {
        f.invoke()
    }

    @Async
    fun <T> callAsync(f: () -> T): CompletableFuture<T>? {
        return CompletableFuture.completedFuture(f.invoke())
    }

    @Async
    fun callAsync(f: () -> Unit, handleException: () -> Unit) {
        try {
            f.invoke()
        } catch (ex: Exception) {
            handleException.invoke()
        }
    }
}
