package com.usadapekora.shared.infrastructure.bus

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.usadapekora.shared.*
import kotlin.concurrent.thread

object RabbitMqConnectionFactory {
    private var connection: Connection? = null

    fun createNewConnection(): Connection {
        val factory = ConnectionFactory()
        factory.username = rabbitMqUser
        factory.password = rabbitMqPassword
        factory.host = rabbitMqHost
        factory.port = rabbitMqPort.toInt()
        factory.virtualHost = rabbitMqVirtualHost
        return factory.newConnection()
    }

    fun getConnection(): Connection {
        if (connection == null) {
            connection = createNewConnection()

            Runtime.getRuntime().addShutdownHook(thread(start = false) {
                connection?.close(500)
                connection = null
            })
        }

        return connection!!
    }

    fun closeConnection() {
        connection?.close(500)
        connection = null
    }
}
