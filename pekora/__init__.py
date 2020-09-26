import dotenv
import os
import pekora.youtube


ROOT = os.path.realpath(os.path.join(os.path.dirname(__file__), '..'))
PACKAGE_DIR = os.path.realpath(os.path.dirname(__file__))
AUDIO_DIR = os.path.join(ROOT, 'assets', 'audio')
DB_DIR = os.path.join(ROOT, 'db')
CHANNEL_ID = 'UC1DCedRgGHBdm81E1llLhOQ'

dotenv.load_dotenv(dotenv_path=os.path.join(ROOT, '.env'))

TOKEN = os.getenv('TOKEN')
ENV = os.getenv('BOT_ENV')
GOOGLE_API_KEY = os.getenv('GOOGLE_API_KEY')
HTTP_BASE_URL = os.getenv('HTTP_BASE_URL')

if ENV == "dev" or ENV == "development":
    import pydevd_pycharm

    pydevd_pycharm.settrace(
        'host.docker.internal',
        port=5000,
        stdoutToServer=True,
        stderrToServer=True
    )

channel_feed = pekora.youtube.YoutubePushNotification(CHANNEL_ID)
