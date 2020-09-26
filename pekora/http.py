from quart import Quart, request, Response
import pekora.youtube
import pekora
import json


http = Quart(__name__)


@http.route('/webhook/pekora/feed', methods=['GET', 'POST'])
async def pekora_upload_notification():
    body: bytes = await request.get_data()

    try:
        video = pekora.youtube.YoutubeFeedVideo(body.decode('utf-8'))
        pekora.channel_feed.push(video)

        return Response(
            response=json.dumps({"status": "ok"}),
            status=200,
            content_type='application/json'
        )
    except pekora.youtube.YoutubeFeedVideoParseError:
        return Response(
            response=json.dumps({
                "status": "error",
                "type": "YoutubeFeedVideoParseError"
            }),
            status=400,
            content_type='application/json'
        )
