package com.usadapekora.bot.infraestructure.video.youtube

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test

class YoutubeFeedSubscriberTest {

    @Test
    fun `should subscribe to youtube feed`() {
        val subscriber = YoutubeFeedSubscriber()

        assertDoesNotThrow {
            runBlocking {
                subscriber.subscribe()
            }
        }
    }

}
