package com.usadapekora.shared.infrastructure

import com.usadapekora.shared.domain.Logger
import org.slf4j.LoggerFactory as Slf4jLibLoggerFactory
import com.usadapekora.shared.domain.LoggerFactory
import kotlin.reflect.KClass

class Slf4jLoggerFactory : LoggerFactory {
    override fun getLogger(name: String): Logger
        = Slf4jLogger(Slf4jLibLoggerFactory.getLogger(name))

    override fun getLogger(name: KClass<*>): Logger
        = Slf4jLogger(Slf4jLibLoggerFactory.getLogger(name.java))
}
