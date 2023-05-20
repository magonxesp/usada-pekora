package com.usadapekora.auth.backend

import com.usadapekora.auth.modules
import com.usadapekora.shared.enableDependencyInjection
import org.koin.core.context.stopKoin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.BeforeTest


@SpringBootTest(
    classes = [HttpApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class SpringBootHttpTestCase {
    @Autowired
    protected lateinit var webClient: WebTestClient

    @BeforeTest
    fun setupDependencyInjection() {
        stopKoin()
        enableDependencyInjection(modules = modules)
    }

    protected fun readResource(name: String): ByteArray {
        val resource = this::class.java.getResource(name) ?: throw RuntimeException("test resource $name not found")
        return resource.readBytes()
    }
}
