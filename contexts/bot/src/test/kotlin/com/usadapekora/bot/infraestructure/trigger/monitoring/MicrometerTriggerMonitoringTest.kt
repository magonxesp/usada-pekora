package com.usadapekora.bot.infraestructure.trigger.monitoring

import kotlin.test.Test

class MicrometerTriggerMonitoringTest {

    val monitoring = MicrometerTriggerMonitoring()

    @Test
    fun `it should register trigger matched`() {
        monitoring.triggerInputMatched()
    }

    @Test
    fun `it should register trigger not matched`() {
        monitoring.triggerInputNotMatched()
    }

    @Test
    fun `it should register trigger text response sent`() {
        monitoring.triggerTextResponseSent()
    }

    @Test
    fun `it should register trigger audio response sent`() {
        monitoring.triggerAudioResponseSent()
    }

}
