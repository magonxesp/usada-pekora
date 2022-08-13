package es.magonxesp.pekorabot.http

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class HttpApplication

fun startHttpServer() {
    runApplication<HttpApplication>()
}
