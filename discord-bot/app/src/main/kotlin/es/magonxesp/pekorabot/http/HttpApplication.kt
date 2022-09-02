package es.magonxesp.pekorabot.http

import es.magonxesp.pekorabot.modules.shared.domain.thread.ThreadRestartOnException
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication
import kotlin.concurrent.thread


@SpringBootApplication(exclude = [MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
open class HttpApplication

fun startHttpServer() {
    val thread = thread(start = true) {
        runApplication<HttpApplication>()
    }

    thread.uncaughtExceptionHandler = ThreadRestartOnException()
}
