from typing import Optional
import urllib3
import json


class YoutubeVideo:

    def __init__(self, item: dict):
        self._id: str = item['id']['videoId']
        self._snippet: dict = item['snippet']

    def get_url(self):
        return 'https://www.youtube.com/watch?v={}'.format(self._id)

    def get_thumbnail(self, size='default') -> str:
        """
        Get the video thumbnail url

        :param size: default, medium, high
        """
        return self._snippet['thumbnails'][size]['url']

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
                    return YoutubeVideo(response['items'][0])
        except urllib3.exceptions.HTTPError:
            pass

        return None
