package com.usadapekora.context.shared.domain

interface KeyValueCacheStorage {
    fun set(key: String, value: String)
    fun get(key: String): String?
}
