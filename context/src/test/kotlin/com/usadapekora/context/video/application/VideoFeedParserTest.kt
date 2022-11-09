package com.usadapekora.context.video.application

import com.usadapekora.context.video.domain.FeedParser
import com.usadapekora.context.video.domain.VideoException
import com.usadapekora.context.video.domain.VideoMother
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class VideoFeedParserTest {

    @Test
    fun `should parse the feed content`() {
        val feedContent = """
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

        val expectedVideo = VideoMother.create()
        val parser = mockk<FeedParser>()
        val feedParser = VideoFeedParser(parser)

        every { parser.parse(feedContent) } returns expectedVideo

        val video = feedParser.parse(feedContent)

        verify { parser.parse(feedContent) }

        assertEquals(expectedVideo, video)
    }

    @Test
    fun `should not parse invalid feed content`() {
        val feedContent = ""
        val parser = mockk<FeedParser>()
        val feedParser = VideoFeedParser(parser)

        every { parser.parse(feedContent) } throws VideoException.FeedParse()

        assertThrows<VideoException.FeedParse> {
            feedParser.parse(feedContent)
        }
    }

}
