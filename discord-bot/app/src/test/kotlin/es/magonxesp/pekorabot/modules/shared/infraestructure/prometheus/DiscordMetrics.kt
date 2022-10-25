package es.magonxesp.pekorabot.modules.shared.infraestructure.prometheus

import es.magonxesp.pekorabot.DependencyInjectionEnabledTest
import kotlin.test.Test

class DiscordMetrics : DependencyInjectionEnabledTest() {

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
