package com.usadapekora.shared.domain.common

interface Logger {
    fun warning(logger: String, message: String, exception: Exception)
    fun warning(logger: String, message: String)
    fun info(logger: String, message: String, exception: Exception)
    fun info(logger: String, message: String)
    fun error(logger: String, message: String, exception: Exception)
    fun error(logger: String, message: String)
}
