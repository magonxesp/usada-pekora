package com.usadapekora.http

import com.usadapekora.context.shared.domain.thread.ExitOnThreadUncaughtException
import io.prometheus.client.exporter.HTTPServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import kotlin.concurrent.thread


@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
open class HttpApplication

fun runSpringApplication() {
    runApplication<HttpApplication>()
}

fun startMetricsHttpServer() {
    thread(start = true) {
        HTTPServer(9001)
    }.apply {
        uncaughtExceptionHandler = ExitOnThreadUncaughtException()
    }
}

fun startHttpServer() {
    val thread = thread(start = true, block = ::runSpringApplication)
    thread.uncaughtExceptionHandler = ExitOnThreadUncaughtException()
}
