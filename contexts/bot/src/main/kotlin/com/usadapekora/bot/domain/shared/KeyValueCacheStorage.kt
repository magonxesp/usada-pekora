package com.usadapekora.bot.domain.shared

interface KeyValueCacheStorage {
    fun set(key: String, value: String)
    fun get(key: String): String?
    fun remove(key: String)
}
