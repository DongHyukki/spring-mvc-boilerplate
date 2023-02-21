package com.donghyukki.logger.context

class RequestContext {

    companion object {

        private val CONTEXT_DATA = ThreadLocal<MutableMap<String, Any?>>()

        fun initContext(type: String) {
            val data = mutableMapOf<String, Any?>()
            data["type"] = type
            CONTEXT_DATA.set(data)
        }

        fun clearContext() {
            CONTEXT_DATA.set(mutableMapOf())
        }

        fun put(key: String, value: Any?) {
            CONTEXT_DATA.get()[key] = value
        }

        fun putException(ex: Exception) {
            CONTEXT_DATA.get()["exception"] = ex.message + "\n" + ex.stackTrace.joinToString("\n") { t -> t.toString() }
        }

        fun get(key: String): Any? {
            return CONTEXT_DATA.get()[key]
        }

        fun toLog(): Map<String, Any?> {
            CONTEXT_DATA.get().remove("requestBody")
            CONTEXT_DATA.get().remove("responseBody")
            return CONTEXT_DATA.get().toMap()
        }
    }
}
