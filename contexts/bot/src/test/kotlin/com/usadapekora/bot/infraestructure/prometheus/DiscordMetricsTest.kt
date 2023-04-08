package com.usadapekora.bot.infraestructure.prometheus

import com.usadapekora.bot.DependencyInjectionEnabledTest
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

    @Test
    fun `should count the youtube video notifications sent`() {
        registerVideoNotification()
    }
}
