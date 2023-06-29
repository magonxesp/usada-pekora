package com.usadapekora.shared.domain

import kotlin.reflect.KClass

interface LoggerFactory {
    fun getLogger(name: String): Logger
    fun getLogger(name: KClass<*>): Logger
}
