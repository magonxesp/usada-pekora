package com.usadapekora.shared.domain

interface KeyValueCacheStorage {
    fun set(key: String, value: String)
    fun get(key: String): String?
    fun remove(key: String)
}
