package es.magonxesp.pekorabot.http

import es.magonxesp.pekorabot.modules.shared.domain.thread.ExitOnThreadUncaughtException
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics
import kotlin.concurrent.thread


@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
@EnablePrometheusMetrics
open class HttpApplication

fun runSpringApplication() {
    runApplication<HttpApplication>()
}

fun startHttpServer() {
    val thread = thread(start = true, block = ::runSpringApplication)
    thread.uncaughtExceptionHandler = ExitOnThreadUncaughtException()
}
