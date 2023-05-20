package com.usadapekora.auth.backend

import com.usadapekora.auth.modules
import com.usadapekora.shared.enableDependencyInjection
import io.prometheus.client.hotspot.DefaultExports
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
open class HttpApplication

fun main() {
    enableDependencyInjection(modules = modules)
    DefaultExports.initialize()

    runApplication<HttpApplication>()
}
