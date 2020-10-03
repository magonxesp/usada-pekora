from quart import Quart, request, Response
from pekora.youtube import notifications_service
import pekora
import json


http = Quart(__name__)


@http.route('/webhook/pekora/feed', methods=['GET', 'POST'])
async def pekora_upload_notification():
    body: bytes = await request.get_data()
    body_str: str = body.decode('utf-8')
    response_status = 200
    response_body = ''

    try:
        pekora.LOGGER.info("Youtube notification received: {}".format(body_str))
        challenge = request.args.get('hub.challenge')

        if challenge:
            response_body = challenge

        if body_str != "":
            video = pekora.youtube.YoutubeFeedVideo(body_str)
            await notifications_service.push(video)
    except pekora.youtube.YoutubeFeedVideoParseError:
        response_body = 'An error ocurred: YoutubeFeedVideoParseError'
        response_status = 400

    return Response(
        response=response_body,
        status=response_status,
        content_type='text/plain'
    )
