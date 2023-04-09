package com.usadapekora.bot.backend.controller.webhook

import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.backend.SpringBootHttpTestCase
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.testDiscordTextChannelId
import com.usadapekora.bot.testDiscordGuildId
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import kotlin.random.Random
import kotlin.test.assertEquals


class YoutubeFeedWebhookControllerTest : SpringBootHttpTestCase() {

    private val creator: GuildPreferenceCreator by inject(GuildPreferenceCreator::class.java)
    private val deleter: GuildPreferenceDeleter by inject(GuildPreferenceDeleter::class.java)

    fun createPreferences(channelId: String? = null) {
        creator.create(testDiscordGuildId, GuildPreferences.GuildPreference.FeedChannelId, channelId ?: testDiscordTextChannelId)
    }

    fun removePreferences() {
        deleter.delete(testDiscordGuildId, GuildPreferences.GuildPreference.FeedChannelId)
    }

    @Serializable
    data class TestFeeds(val xmls: Array<String>)

    fun randomFeedXml(): String
        = Json.decodeFromString<TestFeeds>(readResource("/test_youtube_feed_xmls.json").decodeToString()).xmls.random()

    @Test
    fun `should notify the received feed xml`() {
        val xml = randomFeedXml()

        createPreferences()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/webhook/youtube/feed")
                .contentType(MediaType.APPLICATION_ATOM_XML)
                .content(xml)
        ).andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
        }

        removePreferences()
    }

    @Test
    fun `should not notify the received malformed feed xml`() {
        val xml = ""

        createPreferences()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/webhook/youtube/feed")
                .contentType(MediaType.APPLICATION_ATOM_XML)
                .content(xml)
        ).andExpect {
            assertEquals(HttpStatus.BAD_REQUEST.value(), it.response.status)
        }

        removePreferences()
    }

    @Test
    fun `should not notify by any service exception`() {
        val xml = randomFeedXml()

        createPreferences(Random.nextLong().toString()) // invalid channel id

        mockMvc.perform(
            MockMvcRequestBuilders.post("/webhook/youtube/feed")
                .contentType(MediaType.APPLICATION_ATOM_XML)
                .content(xml)
        ).andExpect {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), it.response.status)
        }

        removePreferences()
    }

    @Test
    fun `should return the hub challenge parameter value`() {
        val challenge = Random.nextInt().toString()

        mockMvc.perform(
            MockMvcRequestBuilders.get("/webhook/youtube/feed?hub.challenge=$challenge")
                .contentType(MediaType.TEXT_PLAIN)
        ).andExpect {
            assertEquals(HttpStatus.OK.value(), it.response.status)
            assertEquals(challenge, it.response.contentAsString)
        }
    }

}
