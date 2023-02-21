package com.donghyukki.common.cache

enum class CacheMetaHolder(
    val cacheName: String,
    val cacheTTL: Long
) {
    // SAMPLE
    USER(CacheKeys.USER, 300L)
    ;
}

class CacheKeys {
    companion object {
        const val USER = "USER"
    }
}
