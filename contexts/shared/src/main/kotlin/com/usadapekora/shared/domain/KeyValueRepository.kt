package com.usadapekora.shared.domain

interface KeyValueRepository {
    fun set(key: String, value: String)
    fun get(key: String): String?
    fun remove(key: String)
}
