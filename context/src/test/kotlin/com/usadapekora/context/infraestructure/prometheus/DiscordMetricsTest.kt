package com.usadapekora.context.infraestructure.prometheus

import com.usadapekora.context.DependencyInjectionEnabledTest
import com.usadapekora.context.infraestructure.prometheus.*
import kotlin.test.Test

class DiscordMetricsTest : DependencyInjectionEnabledTest() {

    init {
        setupTestModules()
    }

    @Test
    fun `should count incoming discord messages`() {
        registerMessageRequest()
    }

    @Test
    fun `should count incoming to be processed discord messages`() {
        registerProccesedMessageRequest()
    }

    @Test
    fun `should count triggers fired`() {
        registerTriggerFired()
    }


    @Test
    fun `should count commands fired`() {
        registerCommandFired()
    }

    @Test
    fun `should count the current discord guild using the bot`() {
        registerGuildCount(4)
    }

}
