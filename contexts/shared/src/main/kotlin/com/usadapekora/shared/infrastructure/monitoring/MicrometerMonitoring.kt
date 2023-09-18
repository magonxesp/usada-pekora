package com.usadapekora.shared.infrastructure.monitoring

import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

object MicrometerMonitoring {
    val micrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    fun Application.installMicrometer() {
        install(MicrometerMetrics) {
            registry = micrometerRegistry
            meterBinders = listOf(
                JvmMemoryMetrics(),
                JvmGcMetrics(),
                ProcessorMetrics(),
                JvmThreadMetrics(),
            )
        }
    }
}

