package com.usadapekora.bot.infraestructure.trigger.monitoring

import com.usadapekora.bot.domain.trigger.TriggerMonitoring
import com.usadapekora.shared.infrastructure.monitoring.MicrometerMonitoring
import com.usadapekora.shared.infrastructure.monitoring.createMetricName
import io.micrometer.core.instrument.Counter

class MicrometerTriggerMonitoring : TriggerMonitoring {

    private fun metric(name: String) = createMetricName("bot", "trigger", name)

    private val triggerInputMatchedTotal: Counter = Counter.builder(metric("input_matched_total"))
        .description("Total of the triggers input matched")
        .register(MicrometerMonitoring.micrometerRegistry)

    private val triggerInputNotMatchedTotal: Counter = Counter.builder(metric("input_not_matched_total"))
        .description("Total of the triggers input not matched")
        .register(MicrometerMonitoring.micrometerRegistry)

    private val triggerTextResponseSentTotal: Counter = Counter.builder(metric("text_response_sent_total"))
        .description("Total of the triggers text response send")
        .register(MicrometerMonitoring.micrometerRegistry)

    private val triggerAudioResponseSentTotal: Counter = Counter.builder(metric("audio_response_sent_total"))
        .description("Total of the triggers audio response send")
        .register(MicrometerMonitoring.micrometerRegistry)

    override fun triggerInputNotMatched() {
        triggerInputNotMatchedTotal.increment()
    }

    override fun triggerInputMatched() {
        triggerInputMatchedTotal.increment()
    }

    override fun triggerTextResponseSent() {
        triggerTextResponseSentTotal.increment()
    }

    override fun triggerAudioResponseSent() {
        triggerAudioResponseSentTotal.increment()
    }
}
