package com.usadapekora.auth.backend

import io.prometheus.client.hotspot.DefaultExports
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
open class HttpApplication

fun main() {
    DefaultExports.initialize()

    runApplication<HttpApplication>()
}
