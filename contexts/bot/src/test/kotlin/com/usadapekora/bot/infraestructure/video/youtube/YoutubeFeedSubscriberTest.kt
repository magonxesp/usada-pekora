package com.usadapekora.bot.infraestructure.video.youtube

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertIs

class YoutubeFeedSubscriberTest {

    @Test
    fun `should subscribe to youtube feed`() {
        val subscriber = YoutubeFeedSubscriber()
        val result = runBlocking { subscriber.subscribe() }

        assertIs<Unit>(result.getOrNull(), result.leftOrNull()?.message)
    }

}
