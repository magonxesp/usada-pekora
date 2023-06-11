package com.usadapekora.bot.backend

import com.usadapekora.bot.modules
import com.usadapekora.shared.enableDependencyInjection
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.koin.core.context.stopKoin
import kotlin.test.BeforeTest


abstract class HttpTestCase {
    @BeforeTest
    fun setupDependencyInjection() {
        stopKoin()
        enableDependencyInjection(modules = modules)
    }

    protected fun readResource(name: String): ByteArray {
        val resource = this::class.java.getResource(name) ?: throw RuntimeException("test resource $name not found")
        return resource.readBytes()
    }

    protected fun withTestApplication(block: suspend ApplicationTestBuilder.() -> Unit) = testApplication {
        environment {
            config = ApplicationConfig("application-test.conf")
        }

        block()
    }
}
