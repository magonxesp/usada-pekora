from quart import Quart, request, Response
from pekora.youtube import notifications_service
import pekora
import json


http = Quart(__name__)


@http.route('/webhook/pekora/feed', methods=['GET', 'POST'])
async def pekora_upload_notification():
    body: bytes = await request.get_data()

    try:
        video = pekora.youtube.YoutubeFeedVideo(body.decode('utf-8'))
        await notifications_service.push(video)

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
