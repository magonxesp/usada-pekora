package es.magonxesp.pekorabot.modules.shared.domain

interface KeyValueCacheStorage {
    fun set(key: String, value: String)
    fun get(key: String): String?
}
