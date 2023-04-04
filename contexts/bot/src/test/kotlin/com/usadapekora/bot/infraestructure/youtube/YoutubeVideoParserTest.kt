package com.usadapekora.bot.infraestructure.youtube

import com.usadapekora.bot.domain.shared.DateTimeUtils
import com.usadapekora.bot.domain.video.VideoException
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class YoutubeVideoParserTest {

    @Test
    fun `should parse xml feed to video object`() {
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

        val parser = YoutubeVideoParser()
        val video = parser.parse(xml)

        assertEquals("H8FWadpDpmk", video.id)
        assertEquals("http://www.youtube.com/watch?v=H8FWadpDpmk", video.url)
        assertEquals("【犬鳴トンネル】ほ…ほほ本当にある心霊スポットにみんなで行こう...！ぺこ！【ホロライブ/兎田ぺこら】", video.title)
        assertEquals(DateTimeUtils.fromISO8061("2015-03-06T21:40:57+00:00"), video.publishDate)
    }

    @Test
    fun `should not parse invalid xml feed to video object`() {
        val xml = """
            <feed xmlns:yt="http://www.youtube.com/xml/schemas/2015"
                     xmlns="http://www.w3.org/2005/Atom">
              <link rel="hub" href="https://pubsubhubbub.appspot.com"/>
              <link rel="self" href="https://www.youtube.com/xml/feeds/videos.xml?channel_id=UC1DCedRgGHBdm81E1llLhOQ"/>
              <title>YouTube video feed</title>
              <updated>2015-04-01T19:05:24.552394234+00:00</updated>
        """

        val parser = YoutubeVideoParser()

        assertThrows<VideoException.FeedParse> {
            parser.parse(xml)
        }
    }

    @Test
    fun `should not parse blank string`() {
        val parser = YoutubeVideoParser()

        assertThrows<VideoException.FeedParse> {
            parser.parse("")
        }
    }

}
