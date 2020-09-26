from typing import Optional
import urllib3
import json
import xml.etree.ElementTree


class YoutubeVideo:

    def __init__(self):
        pass

    def get_url(self):
        raise NotImplementedError

    def get_thumbnail(self, size='default') -> str:
        raise NotImplementedError

    def get_channel_id(self) -> str:
        raise NotImplementedError

    def get_title(self) -> str:
        raise NotImplementedError

    def get_description(self) -> str:
        raise NotImplementedError

    def get_channel_title(self) -> str:
        raise NotImplementedError

    def get_type(self) -> str:
        raise NotImplementedError

    def get_publish_date(self) -> str:
        raise NotImplementedError


class YoutubeVideoSnippet(YoutubeVideo):

    def __init__(self, item: dict):
        super().__init__()
        self._id: str = item['id']['videoId']
        self._snippet: dict = item['snippet']

    def get_url(self):
        return 'https://www.youtube.com/watch?v={}'.format(self._id)

    def get_thumbnail(self, size='default') -> str:
        """
        Get the video thumbnail url

        :param size: default, medium, high
        """
        if 'thumbnails' in self._snippet:
            return self._snippet['thumbnails'][size]['url']

        return ''

    def get_channel_id(self) -> str:
        return self._snippet['channelId']

    def get_title(self) -> str:
        return self._snippet['title']

    def get_description(self) -> str:
        return self._snippet['description']

    def get_channel_title(self) -> str:
        return self._snippet['channelTitle']

    def get_type(self) -> str:
        return self._snippet['liveBroadcastContent']

    def get_publish_date(self) -> str:
        return self._snippet['publishedAt']


class YoutubeFeedVideoParseError(Exception):
    pass


class YoutubeFeedVideo(YoutubeVideo):

    def __init__(self, xml_string: str):
        super().__init__()
        self._namespace_map = {
            'atom': 'http://www.w3.org/2005/Atom',
            'yt': 'http://www.youtube.com/xml/schemas/2015'
        }

        try:
            self._root_elemnt = xml.etree.ElementTree.fromstring(xml_string)
            self._entry = self._root_elemnt.find('atom:entry', namespaces=self._namespace_map)

            if self._entry is None:
                raise YoutubeFeedVideoParseError()
        except xml.etree.ElementTree.ParseError:
            raise YoutubeFeedVideoParseError()

    def get_url(self):
        video_id = self._entry.find('yt:videoId', namespaces=self._namespace_map)

        if video_id is not None:
            return 'https://www.youtube.com/watch?v={}'.format(video_id.text)

        return ''

    def get_thumbnail(self, size='default') -> str:
        return ''

    def get_channel_id(self) -> str:
        channel_id = self._entry.find('yt:channelId', namespaces=self._namespace_map)

        if channel_id is not None:
            return channel_id.text

        return ''

    def get_title(self) -> str:
        title = self._entry.find('atom:title', namespaces=self._namespace_map)

        if title is not None:
            return title.text

        return ''

    def get_description(self) -> str:
        return ''

    def get_channel_title(self) -> str:
        author = self._entry.find('atom:author', namespaces=self._namespace_map)

        if author is not None:
            channel_title = author.find('atom:name', namespaces=self._namespace_map)

            if channel_title is not None:
                return channel_title.text

        return ''

    def get_type(self) -> str:
        return 'video'

    def get_publish_date(self) -> str:
        published = self._entry.find('atom:published', namespaces=self._namespace_map)

        if published is not None:
            return published.text

        return ''


class Youtube:

    def __init__(self, api_key: str):
        self._api_key = api_key
        self._http = urllib3.PoolManager()

    def get_streaming(self, channel_id: str) -> Optional[YoutubeVideo]:
        try:
            request: urllib3.HTTPResponse = self._http.request(
                'GET',
                'https://www.googleapis.com/youtube/v3/search',
                fields={
                    'part': 'snippet',
                    'channelId': channel_id,
                    'eventType': 'live',
                    'maxResults': 1,
                    'type': 'video',
                    'key': self._api_key
                }
            )

            if request.status == 200:
                response = json.loads(request.data.decode('utf-8'))

                if response['pageInfo']['totalResults'] == 1:
                    return YoutubeVideoSnippet(response['items'][0])
        except urllib3.exceptions.HTTPError:
            pass

        return None


class YoutubePushNotification:

    def __init__(self, channel_id: str):
        self._channel_id = channel_id
        self._http = urllib3.PoolManager()
        self._topic_url = 'https://www.youtube.com/xml/feeds/videos.xml?channel_id={}'.format(self._channel_id)

    def _request(self, callback_url: str, mode: str) -> Optional[str]:
        try:
            response: urllib3.HTTPResponse = self._http.request(
                'POST',
                'https://pubsubhubbub.appspot.com/subscribe',
                fields={
                    'hub.mode': mode,
                    'hub.callback': callback_url,
                    'hub.lease_seconds': (60 * 60 * 24 * 365),
                    'hub.topic': self._topic_url,
                    'hub.verify': 'async'
                },
                headers={
                    'Content-type': 'application/x-www-form-urlencoded'
                }
            )

            if response.status == 200 or response.status == 204:
                return response.data.decode('utf-8')
        except urllib3.exceptions.HTTPError:
            pass

        return None

    def is_subscribed(self):
        pass

    def subscribe(self, callback_url: str):
        """ Subscribe to the channel feed """
        response = self._request(callback_url, 'subscribe')
        return response is not None

    def push(self, video: YoutubeVideo):
        """ Send the push notification to discord text channel """
        pass
