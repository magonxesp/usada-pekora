package com.usadapekora.http.controller.webhook

import com.usadapekora.http.HttpApplication
//import com.usadapekora.context.guild.domain.GuildPreferences
//import com.usadapekora.context.guild.domain.GuildPreferencesRepository
//import com.usadapekora.context.shared.domain.DateTimeUtils
//import com.usadapekora.context.video.domain.FeedParser
//import com.usadapekora.context.video.domain.Video
//import com.usadapekora.context.video.domain.VideoException
//import com.usadapekora.context.video.domain.VideoFeedNotifier
//import io.mockk.clearAllMocks
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.koin.dsl.module
//import kotlin.test.Test
//import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.web.reactive.server.WebTestClient
//import java.time.ZonedDateTime
//import java.util.Random
//import kotlin.test.BeforeTest


@SpringBootTest(
    classes = [HttpApplication::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class YoutubeFeedWebhookControllerTest /*: DependencyInjectionEnabledTest()*/ {

    /*@Autowired
    private lateinit var webClient: WebTestClient
    private lateinit var notifier: VideoFeedNotifier
    private lateinit var parser: FeedParser
    private lateinit var preferenceRepository: GuildPreferencesRepository

    @BeforeTest
    fun beforeTest() {
        clearAllMocks()

        notifier = mockk(relaxed = true)
        parser = mockk()
        preferenceRepository = mockk()

        setupTestModules {
            listOf(
                module {
                    factory { notifier }
                    factory { parser }
                    factory { preferenceRepository }
                }
            )
        }
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

        val video = Video(
            title = "【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】",
            url = "http://www.youtube.com/watch?v=H8FWadpDpmk",
            id = "H8FWadpDpmk",
            publishDate = DateTimeUtils.fromISO8061("2015-03-06T21:40:57+00:00")
        )

        val target = Random().nextLong().toString() // discord channel id

        every { parser.parse(xml) } returns video
        every { preferenceRepository.findByPreference(GuildPreferences.GuildPreference.FeedChannelId) } returns arrayOf(GuildPreferencesMother.create(
            preferences = mutableMapOf(GuildPreferences.GuildPreference.FeedChannelId to target)
        ))

        webClient.post().uri("/webhook/youtube/feed")
            .bodyValue(xml)
            .exchange()
            .expectStatus()
            .isOk

        verify { notifier.notify(video, target) }
    }

    @Test
    fun `should not notify the received malformed feed xml`() {
        val xml = ""
        val video = Video(
            title = "【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】",
            url = "http://www.youtube.com/watch?v=H8FWadpDpmk",
            id = "H8FWadpDpmk",
            publishDate = DateTimeUtils.fromISO8061("2015-03-06T21:40:57+00:00")
        )
        val target = Random().nextLong().toString() // discord channel id

        every { parser.parse(xml) } throws VideoException.FeedParse()
        every { preferenceRepository.findByPreference(GuildPreferences.GuildPreference.FeedChannelId) } returns arrayOf(GuildPreferences(
            guildId = "242342314543545",
            preferences = mutableMapOf(GuildPreferences.GuildPreference.FeedChannelId to target)
        ))

        webClient.post().uri("/webhook/youtube/feed")
            .bodyValue(xml)
            .exchange()
            .expectStatus()
            .isBadRequest

        verify(inverse = true) { notifier.notify(video, target) }
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

        val video = Video(
            title = "【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】",
            url = "http://www.youtube.com/watch?v=H8FWadpDpmk",
            id = "H8FWadpDpmk",
            publishDate = DateTimeUtils.fromISO8061("2015-03-06T21:40:57+00:00")
        )

        every { parser.parse(xml) } returns video
        every { preferenceRepository.findByPreference(GuildPreferences.GuildPreference.FeedChannelId) } throws Exception()

        webClient.post().uri("/webhook/youtube/feed")
            .bodyValue(xml)
            .exchange()
            .expectStatus()
            .is5xxServerError
    }

    @Test
    fun `should return the hub challenge parameter value`() {
        val challenge = Random().nextInt().toString()

        webClient.get().uri("/webhook/youtube/feed?hub.challenge=$challenge")
            .exchange()
            .expectStatus()
            .isOk
            .expectBody(String::class.java)
            .isEqualTo(challenge)
    }*/

}
