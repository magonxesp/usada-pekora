from quart import Quart, request
import pekora.youtube
import pekora


http = Quart(__name__)


@http.route('/webhook/pekora/feed')
async def pekora_upload_notification():
    body = await request.data
    video = pekora.youtube.parse_notification(body)
    pekora.channel_feed.push(video)
    return ''
