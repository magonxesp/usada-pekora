package com.usadapekora.backend

import com.usadapekora.context.shared.domain.thread.ExitOnThreadUncaughtException
import io.prometheus.client.exporter.HTTPServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import kotlin.concurrent.thread


@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
open class HttpApplication

fun main() {
    runApplication<HttpApplication>()
}
