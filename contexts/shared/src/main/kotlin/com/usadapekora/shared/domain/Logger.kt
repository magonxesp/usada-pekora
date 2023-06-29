package com.usadapekora.shared.domain

interface Logger {
    fun warning(message: String, exception: Exception)
    fun warning(message: String)
    fun info(message: String, exception: Exception)
    fun info(message: String)
    fun error(message: String, exception: Exception)
    fun error(message: String)
}
