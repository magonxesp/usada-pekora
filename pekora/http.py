from quart import Quart


http = Quart(__name__)


@http.route('/webhook/pekora/feed')
async def pekora_upload_notification():
    return 'hello'
