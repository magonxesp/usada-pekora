package com.usadapekora.context.domain.shared

interface KeyValueCacheStorage {
    fun set(key: String, value: String)
    fun get(key: String): String?
}
