package com.usadapekora.bot.backend

import com.usadapekora.bot.enableDependencyInjection
import org.koin.core.context.stopKoin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import kotlin.test.BeforeTest


@SpringBootTest(
    classes = [HttpApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class SpringBootHttpTestCase {
    @Autowired
    protected lateinit var webApplicationContext: WebApplicationContext
    protected lateinit var mockMvc: MockMvc

    @BeforeTest
    fun setupDependencyInjection() {
        stopKoin()
        enableDependencyInjection()
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    protected fun readResource(name: String): ByteArray {
        val resource = this::class.java.getResource(name) ?: throw RuntimeException("test resource $name not found")
        return resource.readBytes()
    }
}
