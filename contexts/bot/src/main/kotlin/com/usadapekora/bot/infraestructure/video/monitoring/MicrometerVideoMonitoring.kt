package com.usadapekora.bot.infraestructure.video.monitoring

import com.usadapekora.bot.domain.video.VideoMonitoring
import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring
import com.usadapekora.shared.infrastructure.monitoring.createMetricName
import io.micrometer.core.instrument.Counter

class MicrometerVideoMonitoring : VideoMonitoring {

    private fun metric(name: String) = createMetricName("bot", "video", name)

    private val videoNotificationProcessed: Counter = Counter.builder(metric("notification_processed_total"))
        .description("Total of the incoming video notifications processed (incoming notifications from youtube channel)")
        .register(MicrometerMonitoring.micrometerRegistry)

    private val videoNotificationCount: Counter = Counter.builder(metric("notification_sent_total"))
        .description("Total of the video notifications sent to a target (discord channel or whatever)")
        .register(MicrometerMonitoring.micrometerRegistry)

    override fun notificationProcessed() {
        videoNotificationProcessed.increment()
    }

    override fun notificationSentToTarget() {
        videoNotificationCount.increment()
    }
}
