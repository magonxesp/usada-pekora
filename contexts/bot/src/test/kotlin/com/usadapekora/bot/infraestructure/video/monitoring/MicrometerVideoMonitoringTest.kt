package com.usadapekora.bot.infraestructure.trigger.monitoring

import com.usadapekora.bot.infraestructure.video.monitoring.MicrometerVideoMonitoring
import kotlin.test.Test

class MicrometerVideoMonitoringTest {

    val monitoring = MicrometerVideoMonitoring()

    @Test
    fun `it should register notification processed`() {
        monitoring.notificationProcessed()
    }

    @Test
    fun `it should register notification sent to target`() {
        monitoring.notificationSentToTarget()
    }

}
