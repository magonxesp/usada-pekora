package com.usadapekora.bot.backend.routes.webhook

import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.backend.HttpTestCase
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.testDiscordGuildId
import com.usadapekora.bot.testDiscordTextChannelId
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.random.Random
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class YoutubeWebhookTest : HttpTestCase() {

    private val creator: GuildPreferenceCreator by inject(GuildPreferenceCreator::class.java)
    private val deleter: GuildPreferenceDeleter by inject(GuildPreferenceDeleter::class.java)

    @BeforeTest
    fun createPreferences() {
        creator.create(testDiscordGuildId, GuildPreferences.GuildPreference.FeedChannelId, testDiscordTextChannelId)
    }

    @AfterTest
    fun removePreferences() {
        deleter.delete(testDiscordGuildId, GuildPreferences.GuildPreference.FeedChannelId)
    }

    @Serializable
    data class TestFeeds(val xmls: Array<String>)

    private fun randomFeedXml(): String
        = Json.decodeFromString<TestFeeds>(readResource("/test_youtube_feed_xmls.json").decodeToString()).xmls.random()

    @Test
    fun `should notify the received feed xml`() = testApplication {
        val xml = randomFeedXml()

        val response = client.post("/webhook/youtube/feed") {
            contentType(ContentType.Application.Atom)
            setBody(xml)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should notify the received feed xml multiple times`() = testApplication {
        (0..4).forEach { _ ->
            val xml = randomFeedXml()

            val response = client.post("/webhook/youtube/feed") {
                contentType(ContentType.Application.Atom)
                setBody(xml)
            }

            assertEquals(HttpStatusCode.OK, response.status)
            delay(500)
        }
    }

    @Test
    fun `should not notify the received malformed feed xml`() = testApplication {
        val xml = ""

        val response = client.post("/webhook/youtube/feed") {
            contentType(ContentType.Application.Atom)
            setBody(xml)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `should return the hub challenge parameter value`() = testApplication {
        val challenge = Random.nextInt().toString()

        val response = client.get("/webhook/youtube/feed") {
            contentType(ContentType.Text.Plain)
            parameter("hub.challenge", challenge)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(challenge, response.bodyAsText())
    }

}
