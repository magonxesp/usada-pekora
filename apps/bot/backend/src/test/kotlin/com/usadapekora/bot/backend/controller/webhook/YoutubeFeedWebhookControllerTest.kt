package com.usadapekora.bot.backend.controller.webhook

import com.usadapekora.bot.application.guild.GuildPreferenceCreator
import com.usadapekora.bot.application.guild.GuildPreferenceDeleter
import com.usadapekora.bot.backend.SpringBootHttpTestCase
import com.usadapekora.bot.domain.guild.GuildPreferences
import com.usadapekora.bot.testDiscordTextChannelId
import com.usadapekora.bot.testDiscordGuildId
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

    @Test
    fun `should notify the received feed xml`() {
        val xml = """
            <feed xmlns:yt="http://www.youtube.com/xml/schemas/2015"
                     xmlns="http://www.w3.org/2005/Atom">
              <link rel="hub" href="https://pubsubhubbub.appspot.com"/>
              <link rel="self" href="https://www.youtube.com/xml/feeds/videos.xml?channel_id=UC1DCedRgGHBdm81E1llLhOQ"/>
              <title>YouTube video feed</title>
              <updated>2015-04-01T19:05:24.552394234+00:00</updated>
              <entry>
                <id>yt:video:H8FWadpDpmk</id>
                <yt:videoId>H8FWadpDpmk</yt:videoId>
                <yt:channelId>UC1DCedRgGHBdm81E1llLhOQ</yt:channelId>
                <title>【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】</title>
                <link rel="alternate" href="http://www.youtube.com/watch?v=H8FWadpDpmk"/>
                <author>
                 <name>Channel title</name>
                 <uri>http://www.youtube.com/channel/UC1DCedRgGHBdm81E1llLhOQ</uri>
                </author>
                <published>2015-03-06T21:40:57+00:00</published>
                <updated>2015-03-09T19:05:24.552394234+00:00</updated>
              </entry>
            </feed>
        """

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
        val xml = """
            <feed xmlns:yt="http://www.youtube.com/xml/schemas/2015"
                     xmlns="http://www.w3.org/2005/Atom">
              <link rel="hub" href="https://pubsubhubbub.appspot.com"/>
              <link rel="self" href="https://www.youtube.com/xml/feeds/videos.xml?channel_id=UC1DCedRgGHBdm81E1llLhOQ"/>
              <title>YouTube video feed</title>
              <updated>2015-04-01T19:05:24.552394234+00:00</updated>
              <entry>
                <id>yt:video:H8FWadpDpmk</id>
                <yt:videoId>H8FWadpDpmk</yt:videoId>
                <yt:channelId>UC1DCedRgGHBdm81E1llLhOQ</yt:channelId>
                <title>【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】</title>
                <link rel="alternate" href="http://www.youtube.com/watch?v=H8FWadpDpmk"/>
                <author>
                 <name>Channel title</name>
                 <uri>http://www.youtube.com/channel/UC1DCedRgGHBdm81E1llLhOQ</uri>
                </author>
                <published>2015-03-06T21:40:57+00:00</published>
                <updated>2015-03-09T19:05:24.552394234+00:00</updated>
              </entry>
            </feed>
        """

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
